package com.lzy.apis.admin;

import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 14:47 2021/7/28
 * @ Description：铭感词接口
 */

@Api(value = "敏感词管理", tags = "敏感词模块")
public interface AdSensitiveControllerApi {

    /**
     * 分页查找敏感词
     * @param dto
     * @return
     */
    @ApiOperation("根据名称分页查询敏感词列表")
    public ResponseResult list(SensitiveDto dto);

    /**
     * 保存铭感词
     * @param adSensitive
     * @return
     */
    @ApiOperation("保存敏感词")
    public ResponseResult save(AdSensitive adSensitive);

    /**
     * 修改铭感词
     * @param adSensitive
     * @return
     */
    @ApiOperation("修改敏感词")
    public ResponseResult update(AdSensitive adSensitive);
    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @ApiOperation("删除敏感词")
    public ResponseResult delete(Integer id);

}
