package com.itmyiedu.feign;

import com.itmyiedu.api.PayCallBackService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/11/13.
 */
@Component
@FeignClient(value = "pay")
public interface CallBackFeign extends PayCallBackService {
}
