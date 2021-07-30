package com.lzy.apis.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "自媒体登录",tags = "自媒体模块")
public interface LoginControllerApi {

    /**
     * 自媒体登录
     * @param dto
     * @return
     */
    @ApiOperation("自媒体登录")
    public ResponseResult login(WmUserDto dto);
}