package com.lzy.apis.admin;

import com.heima.model.admin.dtos.AdUserDto;

import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author ：lzy
 * @ Date       ：Created in 16:22 2021/7/28
 * @ Description：登录的api接口
 */

@Api(value = "登录接口",tags = "登录模块")
public interface LoginControllerApi {

    @ApiOperation("登录")
    public ResponseResult login(AdUserDto dtos);

}
