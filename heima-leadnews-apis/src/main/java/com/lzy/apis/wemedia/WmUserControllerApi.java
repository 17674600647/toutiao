package com.lzy.apis.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 15:41 2021/7/29
 * @ Description：自媒体控制类Api
 */

@Api(value = "自媒体控制类", tags = "自媒体端")
public interface WmUserControllerApi {

    /**
     * 保存自媒体用户
     *
     * @param wmUser
     * @return
     */
    @ApiOperation("保存自媒体用户")
    public ResponseResult save(WmUser wmUser);

    /**
     * 根据名称查询自媒体用户
     * @param name
     * @return
     */
    @ApiOperation("根据名称查询自媒体用户")
    public WmUser findByName(String name);
}
