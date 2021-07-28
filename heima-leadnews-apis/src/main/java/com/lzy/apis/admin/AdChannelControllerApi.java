package com.lzy.apis.admin;

import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;

/**
 * @author ：lzy
 * @ Date       ：Created in 10:08 2021/7/28
 * @ Description：频道接口
 */

@Api(value = "频道管理", tags = "频道模块")
public interface AdChannelControllerApi {
    /**
     * 根据名称分页查询频道列表
     *
     * @param dto
     * @return
     */
    @ApiOperation("根据名称分页查询频道列表")
    public ResponseResult findByNameAndPage(ChannelDto dto);

    /**
     * 保存频道
     *
     * @param adChannel
     * @return
     */
    @ApiOperation("保存频道")
    public ResponseResult save(AdChannel adChannel);

    /**
     * 修改频道
     *
     * @param adChannel
     * @return
     */
    @ApiOperation("修改频道")
    public ResponseResult update(AdChannel adChannel);


    /**
     * 根据ID删除
     * @param integer
     * @return
     */
    @ApiOperation("根据ID删除")
    public ResponseResult deleteById(Integer integer);
}
