package com.lzy.apis.user;


import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 10:45 2021/7/29
 * @ Description：userApi
 */

@Api(value = "用户模块", tags = "用户模块")
public interface ApUserRealnameControllerApi {

    /**
     * 按照状态进行分页查询用户列表
     *
     * @param dto
     * @return
     */
    @ApiOperation("根据状态查询用户")
    public ResponseResult loadListByStatus(AuthDto dto);


    /**
     * 审核通过
     * @param dto
     * @return
     */
    @ApiOperation("审核通过")
    public ResponseResult authPass(AuthDto dto);

    /**
     * 审核失败
     * @param dto
     * @return
     */
    @ApiOperation("审核失败")
    public ResponseResult authFail(AuthDto dto);
}
