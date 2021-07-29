package com.lzy.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.wemedia.pojos.WmUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 15:45 2021/7/29
 * @ Description：WmUserMapper层
 */

@Mapper
public interface WmUserMapper extends BaseMapper<WmUser> {
}
