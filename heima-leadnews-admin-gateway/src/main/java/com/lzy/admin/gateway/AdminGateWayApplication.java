package com.lzy.admin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：lzy
 * @ Date       ：Created in 8:57 2021/7/29
 * @ Description：admin启动网关
 */

@SpringBootApplication
@EnableDiscoveryClient
public class AdminGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminGateWayApplication.class, args);
    }
}
