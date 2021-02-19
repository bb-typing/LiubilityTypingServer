package org.liubility.account;

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
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}