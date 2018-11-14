package com.itmyiedu.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/9.
 */
@ControllerAdvice
public class ExceptionHandler {
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public Map<String,Object> exceptionHandler(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("code",500);
        map.put("stats","服务器运行错误！");
        return map;
    }
}
