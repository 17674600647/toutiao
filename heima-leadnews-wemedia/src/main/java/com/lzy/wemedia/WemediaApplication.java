package com.lzy.wemedia;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author ：lzy
 * @ Date       ：Created in 15:20 2021/7/29
 * @ Description：启动类
 */
@EnableDiscoveryClient
@ServletComponentScan
@MapperScan("com.lzy.wemedia.mapper")
@SpringBootApplication
public class WemediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class, args);
    }
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
