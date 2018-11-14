package com.itmyiedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by Administrator on 2018/11/11.
 */
@SpringBootApplication
@EnableEurekaClient
public class WeiXinApp {
    public static void main(String[] args) {
        SpringApplication.run(WeiXinApp.class,args);
    }
}
