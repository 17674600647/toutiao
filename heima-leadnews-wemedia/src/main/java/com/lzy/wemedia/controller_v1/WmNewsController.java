package com.lzy.wemedia.controller_v1;


import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;

import com.heima.model.wemedia.pojos.WmNews;
import com.lzy.apis.wemedia.WmNewsControllerApi;
import com.lzy.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController implements WmNewsControllerApi {

    @Autowired
    private WmNewsService wmNewsService;

    @PostMapping("/list")
    @Override
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto wmNewsPageReqDto){
        return wmNewsService.findAll(wmNewsPageReqDto);
    }

    /**
     * 提交文章
     *
     * @param wmNews
     * @return
     */
    @PostMapping("/submit")
    @Override
    public ResponseResult submitNews(@RequestBody WmNewsDto wmNews) {
        if(wmNews.getStatus()== WmNews.Status.SUBMIT.getCode()){
            //提交文章
            return wmNewsService.saveNews(wmNews, WmNews.Status.SUBMIT.getCode());
        }else{
            //保存草稿
            return wmNewsService.saveNews(wmNews, WmNews.Status.NORMAL.getCode());
        }
    }
    @GetMapping("/one/{id}")
    @Override
    public ResponseResult findWmNewsById(@PathVariable("id") Integer id) {
        return wmNewsService.findWmNewsById(id);
    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @GetMapping("/del_news/{id}")
    @Override
    public ResponseResult delNews(@PathVariable("id") Integer id) {
        return wmNewsService.delNews(id);
    }

    /**
     * 上下架
     *
     * @param dto
     * @return
     */
    @PostMapping("/down_or_up")
    @Override
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto) {
        return wmNewsService.downOrUp(dto);
    }
}