package com.itmyiedu.feign;

import com.itmyiedu.api.PayService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/11/10.
 */
@Component
@FeignClient(value = "pay")
public interface PayServiceFegin extends PayService {
}
