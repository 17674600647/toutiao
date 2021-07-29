package com.lzy.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constans.user.UserConstants;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.AuthDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.model.wemedia.pojos.WmUser;
import com.lzy.user.feign.ArticleFeign;
import com.lzy.user.feign.WemediaFeign;
import com.lzy.user.mapper.ApUserMapper;
import com.lzy.user.mapper.ApUserRealnameMapper;
import com.lzy.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author ：lzy
 * @ Date       ：Created in 10:58 2021/7/29
 * @ Description：实现类
 */
@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {
    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private WemediaFeign wemediaFeign;

    @Autowired
    private ArticleFeign articleFeign;

    @Override
    public ResponseResult loadListByStatus(AuthDto dto) {
        //检查参数
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //分页查询检查
        dto.checkParam();
        //根据分页状态查询
        LambdaQueryWrapper<ApUserRealname> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (dto.getStatus() != null) {
            lambdaQueryWrapper.eq(ApUserRealname::getStatus, dto.getStatus());
        }

        IPage pageParameter = new Page(dto.getPage(), dto.getSize());
        IPage page = page(pageParameter, lambdaQueryWrapper);
        //返回结果
        PageResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());
        return responseResult;
    }

    @Override
    public ResponseResult updateStatusById(AuthDto dto, Short status) {
        //检查参数
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //检查状态
        if (checkStatus(status)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //修改状态
        ApUserRealname apUserRealname = new ApUserRealname();
        apUserRealname.setId(dto.getId());
        apUserRealname.setStatus(status);

        if (dto.getMsg() != null) {
            apUserRealname.setReason(dto.getMsg());
        }

        updateById(apUserRealname);

        //如果审核通过,就创建自媒体账户,创建作者信息
        if (status.equals(UserConstants.PASS_AUTH)) {
            //创建自媒体账户,创建作者信息
            ResponseResult result = createWmUserAndAuthor(dto);
            if (result != null) {
                return result;
            }
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    /**
     * 创建自媒体账户,创建作者信息
     *
     * @param dto
     */
    private ResponseResult createWmUserAndAuthor(AuthDto dto) {
        //创建ap_user信息
        Integer UserRealnameId = dto.getId();

        //传入信息验证表的Id查询出相关的信息
        ApUserRealname byId = getById(UserRealnameId);
        //获取Realname表的UserID,并查询相关信息
        ApUser apUser = apUserMapper.selectById(byId.getUserId());
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmUser wmUser = wemediaFeign.findByName(apUser.getName());
        //创建自媒体账户
        if (wmUser == null) {
            wmUser = new WmUser();
            wmUser.setApUserId(apUser.getId());
            wmUser.setCreatedTime(new Date());
            wmUser.setName(apUser.getName());
            wmUser.setPassword(apUser.getPassword());
            wmUser.setSalt(apUser.getSalt());
            wmUser.setPhone(apUser.getPhone());
            wmUser.setStatus((int) UserConstants.PASS_AUTH);
            wemediaFeign.save(wmUser);
        }
        //创建作者
        createAuthor(wmUser);
        apUser.setFlag((short) 1);
        apUserMapper.updateById(apUser);
        return null;
    }

    /**
     * 创建作者
     *
     * @param wmUser
     */
    private void createAuthor(WmUser wmUser) {
        Integer apUserId = wmUser.getApUserId();
        ApAuthor apAuthor = articleFeign.findByUserId(apUserId);
        if (apAuthor == null) {
            apAuthor = new ApAuthor();
            apAuthor.setName(wmUser.getName());
            apAuthor.setCreatedTime(new Date());
            apAuthor.setUserId(apUserId);
            apAuthor.setType(UserConstants.AUTH_TYPE);
            articleFeign.save(apAuthor);
        }
    }


    /**
     * 检查状态
     *
     * @param status
     * @return
     */
    private boolean checkStatus(Short status) {
        if (status == null || (!status.equals(UserConstants.FAIL_AUTH) && !status.equals(UserConstants.PASS_AUTH))) {
            return true;
        }
        return false;
    }
}
