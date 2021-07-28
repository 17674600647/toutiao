package com.lzy.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.admin.pojos.AdSensitive;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 14:52 2021/7/28
 * @ Description：敏感词的接口
 */
@Mapper
public interface AdSensitiveMapper extends BaseMapper<AdSensitive> {
}
