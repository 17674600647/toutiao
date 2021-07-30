package com.lzy.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 19:32 2021/7/30
 * @ Description：
 */
public interface WmMaterialService extends IService<WmMaterial> {
    /**
     * 素材上传图片
     *
     * @return
     */
    public ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 查询列表
     *
     * @param dto
     * @return
     */
    public ResponseResult findList(WmMaterialDto dto);
    /**
     * 删除图片
     * @param id
     * @return
     */
    ResponseResult delPicture(Integer id);
    /**
     * 收藏与取消收藏
     * @param type
     * @return
     */
    ResponseResult updateStatus(Integer id, Short type);
}
