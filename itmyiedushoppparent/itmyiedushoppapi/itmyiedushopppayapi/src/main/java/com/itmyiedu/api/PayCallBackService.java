package com.itmyiedu.api;

import com.itmyiedu.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by Administrator on 2018/11/13.
 */
public interface PayCallBackService {
    @RequestMapping("return_callback")
    public ResponseBase synCallBack(@RequestParam Map<String,String> params);

    @RequestMapping("notify_callback")
    public String asynCallBack(@RequestParam Map<String,String> params);

}
