package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lzy
 * @ Date       ：Created in 10:09 2021/7/28
 * @ Description：频道列表对象
 */
@Data
public class ChannelDto extends PageRequestDto {

    @ApiModelProperty("频道名称")
    private String name;

}
