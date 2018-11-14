package com.itmyiedu.controller;

import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.constants.Constants;
import com.itmyiedu.feign.CallBackFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/13.
 */
@Slf4j
@Controller
@RequestMapping("callback")
public class CallBackController {
    @Autowired
    private CallBackFeign callBackFeign;

    private static final String PAY_SUCCESS = "pay_success";

    @RequestMapping("return_url")
    public void call_return(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        Map<String,String> params=getPayParm(request,false);
        ResponseBase returnResponseBase=callBackFeign.synCallBack(params);
        if(!returnResponseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)){
            writer.println(returnResponseBase.getMsg());
            return;
        }
        LinkedHashMap data = (LinkedHashMap) returnResponseBase.getData();
        String outTradeNo = (String) data.get("out_trade_no");
        // 支付宝交易号
        String tradeNo = (String) data.get("trade_no");
        // 付款金额
        String totalAmount = (String) data.get("total_amount");

        log.info("###支付宝同步回调CallBackController####synCallBack結束 params:{}", params);
        // 封装成html 浏览器模拟去提交
        String htmlFrom="<form name='punchout_form' "
                + "method='post' "
                + "action='http://127.0.0.1/callback/synSuccessPage' >"
                + "<input type='hidden' name='outTradeNo' value='"+outTradeNo+"'>"
                + "<input type='hidden' name='tradeNo' value='"+tradeNo+"'>"
                + "<input type='hidden' name='totalAmount' value='"+totalAmount+"'>"
                + "<input type='submit' value='立即支付' style='display:none'>"
                + "</form>"
                + "<script>document.forms[0].submit();</script>";
        writer.println(htmlFrom);
        writer.flush();
        writer.close();
    }

    // 以post表达隐藏参数
    @RequestMapping(value = "/synSuccessPage", method = RequestMethod.POST)
    public String synSuccessPage(HttpServletRequest request, String outTradeNo, String tradeNo, String totalAmount) {
        request.setAttribute("outTradeNo", outTradeNo);
        request.setAttribute("tradeNo", tradeNo);
        request.setAttribute("totalAmount", totalAmount);
        return PAY_SUCCESS;
    }

    @RequestMapping("notify_url")
    public String call_notify(HttpServletRequest request){
        Map<String,String> params=getPayParm(request,true);
        String asynResult=callBackFeign.asynCallBack(params);
        return asynResult;
    }

    private Map<String,String> getPayParm(HttpServletRequest request,boolean IsCallBackNotify){
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                if(!IsCallBackNotify) {
                    valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        return params;
    }
}
