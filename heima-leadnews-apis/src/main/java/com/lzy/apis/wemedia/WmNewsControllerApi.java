package com.lzy.apis.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 12:19 2021/7/31
 * @ Description：新闻控制类
 */
@Api(value = "文章模块", tags = "文章模块")
public interface WmNewsControllerApi {


     /**
     * 分页带条件查询自媒体文章列表
     * @param wmNewsPageReqDto
     * @return
     */
     @ApiOperation("分页带条件查询自媒体文章列表")
    public ResponseResult findAll(WmNewsPageReqDto wmNewsPageReqDto);

    /**
     * 提交文章
     * @param wmNews
     * @return
     */
    @ApiOperation("提交文章")
    ResponseResult submitNews(WmNewsDto wmNews);

    /**
     * 根据id获取文章信息
     * @return
     */
    @ApiOperation("根据id获取文章信息")
    ResponseResult findWmNewsById(Integer id);

    /**
     * 删除文章
     * @return
     */
    @ApiOperation("删除文章")
    ResponseResult delNews(Integer id);

    /**
     * 上下架
     * @param dto
     * @return
     */
    @ApiOperation("上架下架文章")
    ResponseResult downOrUp(WmNewsDto dto);
}
