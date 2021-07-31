package com.lzy.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.heima.model.wemedia.pojos.WmNewsMaterial;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {
    void saveRelations(@Param("materials") List<String> materials, @Param("newsId") Integer newsId, @Param("type") int type);

}