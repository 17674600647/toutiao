package com.lzy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import com.heima.model.user.pojos.ApUserRealname;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 10:54 2021/7/29
 * @ Description：service层
 */
public interface ApUserRealnameService extends IService<ApUserRealname> {
    /**
     * 按照状态进行分页查询用户列表
     *
     * @param dto
     * @return
     */
    public ResponseResult loadListByStatus(AuthDto dto);

    /**
     * 修改认证用户状态
     * @param dto
     * @param status
     * @return
     */
    public ResponseResult updateStatusById(AuthDto dto,Integer status);
}
