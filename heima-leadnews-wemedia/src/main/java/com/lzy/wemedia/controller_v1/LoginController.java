package com.lzy.wemedia.controller_v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmUserDto;
import com.lzy.apis.wemedia.LoginControllerApi;
import com.lzy.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lzy
 * @ Date       ：Created in 18:23 2021/7/30
 * @ Description：
 */

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {
    @Autowired
    private WmUserService wmUserService;


    @PostMapping("/in")
    @Override
    public ResponseResult login(@RequestBody WmUserDto dto) {
        return wmUserService.login(dto);
    }

}
