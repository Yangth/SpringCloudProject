package com.itmyiedu.feign;

import com.itmyiedu.api.OrderService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/11/13.
 */
@Component
@FeignClient("order")
public interface OrderFeign extends OrderService{
}
