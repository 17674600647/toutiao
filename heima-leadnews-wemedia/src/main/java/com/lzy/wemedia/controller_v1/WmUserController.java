package com.lzy.wemedia.controller_v1;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmUser;
import com.lzy.apis.wemedia.WmUserControllerApi;
import com.lzy.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：lzy
 * @ Date       ：Created in 15:48 2021/7/29
 * @ Description：
 */
@RestController
@RequestMapping("/api/v1/user")
public class WmUserController implements WmUserControllerApi {

    @Autowired
    private WmUserService wmUserService;

    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody WmUser wmUser) {
        boolean save = wmUserService.save(wmUser);
        if(save){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }else {
            return ResponseResult.okResult(AppHttpCodeEnum.PARAM_INVALID);
        }

    }

    @GetMapping("/findByName/{name}")
    @Override
    public WmUser findByName(@PathVariable String name) {
        List<WmUser> list = wmUserService.list(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, name));
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
