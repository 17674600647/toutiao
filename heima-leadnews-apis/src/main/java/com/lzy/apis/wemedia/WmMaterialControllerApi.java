package com.lzy.apis.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：lzy
 * @ Date       ：Created in 19:23 2021/7/30
 * @ Description：控制类
 */
@Api(value = "素材模块", tags = "上传图片模块")
public interface WmMaterialControllerApi {
    /**
     * 素材上传图片
     *
     * @return
     */
    @ApiOperation("上传图片")
    public ResponseResult uploadPicture(MultipartFile multipartFile);
    /**
     * 素材上传图片
     *
     * @return
     */
    @ApiOperation("查看图片")
    public ResponseResult findList(WmMaterialDto dto);
    /**
     * 删除图片
     * @param id
     * @return
     */
    @ApiOperation("删除图片")
    ResponseResult delPicture(Integer id);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @ApiOperation("取消收藏")
    ResponseResult cancleCollectionMaterial(Integer id);

    /**
     * 收藏图片
     * @param id
     * @return
     */
    @ApiOperation("收藏图片")
    ResponseResult collectionMaterial(Integer id);


}
