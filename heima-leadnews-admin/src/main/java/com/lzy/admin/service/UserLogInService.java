package com.lzy.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author ：lzy
 * @ Date       ：Created in 16:27 2021/7/28
 * @ Description：用户类
 */
public interface UserLogInService extends IService<AdUser> {
    public ResponseResult login(AdUserDto dto);
}
