package com.lzy.wemedia.controller_v1;

import com.heima.common.constans.wemedia.WemediaContans;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.lzy.apis.wemedia.WmMaterialControllerApi;
import com.lzy.wemedia.service.WmMaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：lzy
 * @ Date       ：Created in 19:56 2021/7/30
 * @ Description：
 */
@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController implements WmMaterialControllerApi {

    @Autowired
    private WmMaterialService wmMaterialService;

    @PostMapping("/upload_picture")
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        return wmMaterialService.uploadPicture(multipartFile);
    }

    @PostMapping("/list")
    @Override
    public ResponseResult findList(@RequestBody WmMaterialDto dto) {
        return wmMaterialService.findList(dto);
    }

    @GetMapping("/del_picture/{id}")
    @Override
    public ResponseResult delPicture(@PathVariable("id") Integer id) {
        return wmMaterialService.delPicture(id);
    }


    @GetMapping("/cancel_collect/{id}")
    @Override
    public ResponseResult cancleCollectionMaterial(@PathVariable("id") Integer id) {
        return wmMaterialService.updateStatus(id, WemediaContans.CANCEL_COLLECT_MATERIAL);
    }

    @GetMapping("/collect/{id}")
    @Override
    public ResponseResult collectionMaterial(@PathVariable("id") Integer id) {
        return wmMaterialService.updateStatus(id, WemediaContans.COLLECT_MATERIAL);
    }
}
