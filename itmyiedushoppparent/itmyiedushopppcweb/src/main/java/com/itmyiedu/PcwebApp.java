package com.itmyiedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by Administrator on 2018/11/9.
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class PcwebApp {
    public static void main(String[] args) {
        SpringApplication.run(PcwebApp.class,args);
    }
}
