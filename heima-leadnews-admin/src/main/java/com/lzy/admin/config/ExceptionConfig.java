package com.lzy.admin.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：lzy
 * @ Date       ：Created in 13:45 2021/7/28
 * @ Description：全局异常处理类
 */

@Configuration
@ComponentScan("com.heima.common.exception")
public class ExceptionConfig {
}
