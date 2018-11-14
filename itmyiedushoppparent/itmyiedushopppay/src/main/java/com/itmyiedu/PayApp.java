package com.itmyiedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@MapperScan(basePackages = {"com.itmyiedu.mapper"})
public class PayApp {

	 public static void main(String[] args) {
         SpringApplication.run(PayApp.class, args);
	}
	
}
