package com.lzy.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmUserDto;
import com.heima.model.wemedia.pojos.WmUser;

/**
 * @author ：lzy
 * @ Date       ：Created in 15:46 2021/7/29
 * @ Description：
 */
public interface WmUserService extends IService<WmUser> {
    public ResponseResult login(WmUserDto dto);

}
