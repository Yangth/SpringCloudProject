package com.itmyiedu.feign;

import com.itmyiedu.api.MemberServiceAPI;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/11/9.
 */
@Component
@FeignClient("member")
public interface MemberFeigin extends MemberServiceAPI{
}
