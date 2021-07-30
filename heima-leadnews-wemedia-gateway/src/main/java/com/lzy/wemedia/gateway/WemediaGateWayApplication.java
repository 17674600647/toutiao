package com.lzy.wemedia.gateway;

import org.apache.commons.io.filefilter.AgeFileFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：lzy
 * @ Date       ：Created in 18:48 2021/7/30
 * @ Description：
 */

@SpringBootApplication
@EnableDiscoveryClient
public class WemediaGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaGateWayApplication.class, args);
    }
}
