package com.lzy.wemedia.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constans.wemedia.WemediaContans;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;

import com.lzy.wemedia.mapper.WmMaterialMapper;
import com.lzy.wemedia.mapper.WmNewsMapper;
import com.lzy.wemedia.mapper.WmNewsMaterialMapper;
import com.lzy.wemedia.service.WmNewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Value("${fdfs.url}")
    private String fileServerUrl;

    @Override
    public ResponseResult findAll(WmNewsPageReqDto dto) {
        //1.参数检查
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //分页参数检查
        dto.checkParam();

        //2.分页条件查询
        IPage pageParam = new Page(dto.getPage(), dto.getSize());

        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper();
        //状态精确查询
        if (dto.getStatus() != null) {
            lambdaQueryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }
        //频道精确查询
        if (null != dto.getChannelId()) {
            lambdaQueryWrapper.eq(WmNews::getChannelId, dto.getChannelId());
        }

        //时间范围查询
        if (dto.getBeginPubdate() != null && dto.getEndPubdate() != null) {
            lambdaQueryWrapper.between(WmNews::getPublishTime, dto.getBeginPubdate(), dto.getEndPubdate());
        }

        //关键字模糊查询
        if (null != dto.getKeyWord()) {
            lambdaQueryWrapper.like(WmNews::getTitle, dto.getKeyWord());
        }

        //查询当前登录用户的信息
        WmUser user = WmThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        lambdaQueryWrapper.eq(WmNews::getUserId, user.getId());

        //按照创建日期倒序
        lambdaQueryWrapper.orderByDesc(WmNews::getCreatedTime);

        IPage pageResult = page(pageParam, lambdaQueryWrapper);

        //3.结果封装返回
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) pageResult.getTotal());
        responseResult.setData(pageResult.getRecords());
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    /**
     * 自媒体文章发布
     *
     * @param dto
     * @param isSubmit 是否为提交 1 为提交 0为草稿
     * @return
     */
    @Override
    public ResponseResult saveNews(WmNewsDto dto, Short isSubmit) {
        //1.检查参数
        if (dto == null || StringUtils.isBlank(dto.getContent())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.保存或修改文章
        WmNews wmNews = new WmNews();
        BeanUtils.copyProperties(dto, wmNews);
        if (WemediaContans.WM_NEWS_TYPE_AUTO.equals(dto.getType())) {
            wmNews.setType(null);
        }
        if (dto.getImages() != null && dto.getImages().size() > 0) {
            //[dfjksdjfdfj.jpg,sdlkjfskld.jpg]
            wmNews.setImages(dto.getImages().toString().replace("[", "")
                    .replace("]", "").replace(fileServerUrl, "")
                    .replace(" ", ""));
        }
        //保存或修改文章
        saveWmNews(wmNews, isSubmit);


        //3.关联文章与素材的关系
        String content = dto.getContent();
        List<Map> list = JSON.parseArray(content, Map.class);
        List<String> materials = ectractUrlInfo(list);

        //3.1 关联内容中的图片与素材的关系
        if (isSubmit == WmNews.Status.SUBMIT.getCode() && materials.size() != 0) {
            ResponseResult responseResult = saveRelativeInfoForContent(materials, wmNews.getId());
            if (responseResult != null) {
                return responseResult;
            }
        }

        //3.2 关联封面中的图片与素材的关系,设置wm_news的type,自动
        if (isSubmit == WmNews.Status.SUBMIT.getCode()) {
            ResponseResult responseResult = saveRelativeInfoForCover(dto, materials, wmNews);
            if (responseResult != null) {
                return responseResult;
            }
        }


        return null;
    }

    /**
     * 根据文章id查询文章
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult findWmNewsById(Integer id) {
        //1.参数检查
        if(id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"文章Id不可缺少");
        }
        //2.查询数据
        WmNews wmNews = getById(id);
        if(wmNews == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"文章不存在");
        }

        //3.结果返回
        ResponseResult responseResult = ResponseResult.okResult(wmNews);
        responseResult.setHost(fileServerUrl);
        return responseResult;
    }

    /**
     * 设置封面图片与素材的关系
     *
     * @param dto
     * @param materials
     * @param wmNews
     * @return
     */
    private ResponseResult saveRelativeInfoForCover(WmNewsDto dto, List<String> materials, WmNews wmNews) {
        List<String> images = dto.getImages();
        //自动匹配封面
        if (dto.getType().equals(WemediaContans.WM_NEWS_TYPE_AUTO)) {
            //内容中的图片数量小于等于2  设置为单图
            if (materials.size() > 0 && materials.size() <= 2) {
                wmNews.setType(WemediaContans.WM_NEWS_SINGLE_IMAGE);
                images = materials.stream().limit(1).collect(Collectors.toList());
            } else if (materials.size() > 2) {
                //如果内容中的图片大于2 则设置为多图
                wmNews.setType(WemediaContans.WM_NEWS_MANY_IMAGE);
                images = materials.stream().limit(3).collect(Collectors.toList());
            } else {
                //内容中没有图片，则是无图
                wmNews.setType(WemediaContans.WM_NEWS_NONE_IMAGE);
            }
            //修改文章信息
            if (images != null && images.size() > 0) {
                wmNews.setImages(images.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(fileServerUrl, "")
                        .replace(" ", ""));

            }
            updateById(wmNews);
        }
        //保存封面图片与素材的关系
        if (images != null && images.size() > 0) {
            ResponseResult responseResult = saveRelativeInfoForImage(images, wmNews.getId());
            if (responseResult != null) {
                return responseResult;
            }
        }
        return null;
    }

    /**
     * @param images
     * @param newsId
     * @return
     */
    private ResponseResult saveRelativeInfoForImage(List<String> images, Integer newsId) {
        List<String> materials = new ArrayList<>();
        for (String image : images) {
            materials.add(image.replace(fileServerUrl, ""));
        }

        return saveRelativeInfo(materials, newsId, WemediaContans.WM_COVER_REFERENCE);
    }

    /**
     * 保存素材与文章内容的关系
     *
     * @param materials
     * @param newsId
     * @return
     */
    private ResponseResult saveRelativeInfoForContent(List<String> materials, Integer newsId) {
        return saveRelativeInfo(materials, newsId, WemediaContans.WM_CONTENT_REFERENCE);
    }

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    /**
     * 保存关系
     *
     * @param materials
     * @param newsId
     * @param type
     * @return
     */
    private ResponseResult saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
        //1.获取数据库中的素材信息
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(WmMaterial::getUrl, materials);
        lambdaQueryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtils.getUser().getId());
        List<WmMaterial> dbMaterials = wmMaterialMapper.selectList(lambdaQueryWrapper);
        //2.通过图片的路径获取素材的id
        List<String> materialsIds = new ArrayList<>();
        if (dbMaterials != null && dbMaterials.size() > 0) {
            //<kdjfksdjkfljdsf.jps,2>
            Map<String, Integer> uriIdMap = dbMaterials.stream().collect(Collectors.toMap(WmMaterial::getUrl, WmMaterial::getId));
            for (String val : materials) {
                String materialId = String.valueOf(uriIdMap.get(val));
                //没找到
                if ("null".equals(materialId)) {
                    return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "应用图片失效");
                }
                //找到了
                materialsIds.add(materialId);
            }
        }

        //3.批量保存数据
        wmNewsMaterialMapper.saveRelations(materialsIds, newsId, type);
        return null;
    }

    /**
     * 提取图片信息
     *
     * @param list
     * @return
     */
    private List<String> ectractUrlInfo(List<Map> list) {
        List<String> materials = new ArrayList<>();
        for (Map map : list) {
            if (map.get("type").equals(WemediaContans.WM_NEWS_TYPE_IMAGE)) {
                String imgUrl = (String) map.get("value");
                imgUrl = imgUrl.replace(fileServerUrl, "");
                materials.add(imgUrl);
            }
        }
        return materials;
    }

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    /**
     * 保存或修改文章
     *
     * @param wmNews
     * @param isSubmit
     */
    private void saveWmNews(WmNews wmNews, Short isSubmit) {
        wmNews.setStatus(isSubmit);
        wmNews.setUserId(WmThreadLocalUtils.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setEnable((short) 1);
        if (wmNews.getId() == null) {
            save(wmNews);
        } else {
            //如果是修改，则先删除素材与文章的关系
            LambdaQueryWrapper<WmNewsMaterial> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(WmNewsMaterial::getNewsId, wmNews.getId());
            wmNewsMaterialMapper.delete(queryWrapper);
            updateById(wmNews);
        }
    }

    @Override
    public ResponseResult delNews(Integer id) {
        //1.检查参数
        if(id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"文章Id不可缺少");
        }
        //2.获取数据
        WmNews wmNews = getById(id);
        if(wmNews == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"文章不存在");
        }

        //3.判断当前文章的状态  status==9  enable == 1
        if(wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode()) && wmNews.getEnable().equals(WemediaContans.WM_NEWS_ENABLE_UP)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"文章已发布，不能删除");
        }

        //4.去除素材与文章的关系
        wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId,wmNews.getId()));

        //5.删除文章
        removeById(wmNews.getId());
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult downOrUp(WmNewsDto dto) {
        //1.检查参数
        if(dto == null || dto.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.查询文章
        WmNews wmNews = getById(dto.getId());
        if(wmNews == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"文章不存在");
        }

        //3.判断文章是否发布
        if(!wmNews.getStatus().equals(WmNews.Status.PUBLISHED.getCode())){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"当前文章不是发布状态，不能上下架");
        }

        //4.修改文章状态，同步到app端（后期做）TODO
        if(dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2){
            update(Wrappers.<WmNews>lambdaUpdate().eq(WmNews::getId,dto.getId()).set(WmNews::getEnable,dto.getEnable()));
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}