package org.liubility.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Jdragon
 * @Class: SecurityApplication
 * @Date: 2021.02.08 上午 1:49
 * @Description:
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.liubility.api")
@ComponentScan(basePackages = "org.liubility")
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
