package com.lzy.admin.controller_v1;

import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.lzy.admin.service.UserLogInService;
import com.lzy.apis.admin.LoginControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lzy
 * @ Date       ：Created in 16:57 2021/7/28
 * @ Description：登录
 */

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {


    @Autowired
    private UserLogInService userLogInService;

    @PostMapping("/in")
    @Override
    public ResponseResult login(AdUserDto dto) {
        return userLogInService.login(dto);
    }
}
