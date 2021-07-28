package com.lzy.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @ Author     ：lzy
 * @ Date       ：Created in 14:53 2021/7/28
 * @ Description：敏感词Service接口
 */
public interface AdSensitiveService extends IService<AdSensitive> {
    /**
     * 分页查找敏感词
     * @param dto
     * @return
     */
    public ResponseResult list(SensitiveDto dto);

    /**
     * 保存铭感词
     * @param adSensitive
     * @return
     */
    public ResponseResult insert(AdSensitive adSensitive);

    /**
     * 修改铭感词
     * @param adSensitive
     * @return
     */
    public ResponseResult update(AdSensitive adSensitive);
    /**
     * 删除敏感词
     * @param dto
     * @return
     */
    public ResponseResult delete(Integer id);

}
