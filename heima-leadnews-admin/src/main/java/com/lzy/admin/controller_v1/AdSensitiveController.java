package com.lzy.admin.controller_v1;

import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;
import com.lzy.admin.service.AdSensitiveService;
import com.lzy.apis.admin.AdSensitiveControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：lzy
 * @ Date       ：Created in 14:52 2021/7/28
 * @ Description：敏感次接口
 */

@RestController
@RequestMapping("/api/v1/sensitive")
public class AdSensitiveController implements AdSensitiveControllerApi {

    @Autowired
    private AdSensitiveService adSensitiveService;

    @PostMapping("/list")
    @Override
    public ResponseResult list(@RequestBody SensitiveDto dto) {
        return adSensitiveService.list(dto);
    }

    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody AdSensitive adSensitive) {


        return adSensitiveService.insert(adSensitive);
    }

    @PostMapping("/update")
    @Override
    public ResponseResult update(@RequestBody AdSensitive adSensitive) {

        return adSensitiveService.update(adSensitive);
    }

    @DeleteMapping("/del/{id}")
    @Override
    public ResponseResult delete(@RequestBody Integer id) {
        return adSensitiveService.delete(id);
    }
}
