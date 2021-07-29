package com.lzy.wemedia.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmUser;
import com.lzy.wemedia.mapper.WmUserMapper;
import com.lzy.wemedia.service.WmUserService;
import org.springframework.stereotype.Service;

/**
 * @author ：lzy
 * @ Date       ：Created in 15:47 2021/7/29
 * @ Description：实现类
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

}
