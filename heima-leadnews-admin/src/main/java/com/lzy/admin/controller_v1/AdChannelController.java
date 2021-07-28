package com.lzy.admin.controller_v1;

import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import com.lzy.admin.service.AdChannelService;
import com.lzy.apis.admin.AdChannelControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：lzy
 * @ Date       ：Created in 10:34 2021/7/28
 * @ Description：控制层
 */
@RestController
@RequestMapping("/api/v1/channel")
public class AdChannelController implements AdChannelControllerApi {
    @Autowired
    private AdChannelService channelService;

    @PostMapping("/list")
    @Override
    public ResponseResult findByNameAndPage(@RequestBody ChannelDto dto) {
        return channelService.findByNameAndPage(dto);
    }

    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody AdChannel adChannel) {
        return channelService.saveChannel(adChannel);
    }

    @PostMapping("/update")
    @Override
    public ResponseResult update(@RequestBody AdChannel adChannel) {
        return channelService.update(adChannel);
    }

    @GetMapping("/del/{id}")
    @Override
    public ResponseResult deleteById( @PathVariable Integer id) {
        return channelService.deleteById(id);
    }
}
