package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

import java.util.Date;

/**
 * @author ：lzy
 * @ Date       ：Created in 12:21 2021/7/31
 * @ Description：文章请求的dto
 */

@Data
public class WmNewsPageReqDto extends PageRequestDto {
    private Short status;//状态
    private Date beginPubdate;//开始时间
    private Date endPubdate;//结束时间
    private Integer channelId;//所属频道ID
    private String keyWord;//关键字
}
