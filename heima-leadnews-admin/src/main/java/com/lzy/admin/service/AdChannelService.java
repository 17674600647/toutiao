package com.lzy.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author ：lzy
 * @ Date       ：Created in 10:16 2021/7/28
 * @ Description：
 */
public interface AdChannelService extends IService<AdChannel> {
    /**
     * 根据名称分页查询频道列表
     *
     * @param dto
     * @return
     */
    public ResponseResult findByNameAndPage(ChannelDto dto);

    /**
     * 保存频道
     *
     * @param adChannel
     * @return
     */
    public ResponseResult saveChannel(AdChannel adChannel);

    /**
     * 修改频道
     *
     * @param adChannel
     * @return
     */

    public ResponseResult update(AdChannel adChannel);

    /**
     * 根据ID删除
     * @param integer
     * @return
     */
    public ResponseResult deleteById(Integer integer);
}
