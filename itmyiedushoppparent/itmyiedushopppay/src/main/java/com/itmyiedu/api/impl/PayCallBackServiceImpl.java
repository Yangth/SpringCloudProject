package com.itmyiedu.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.codingapi.tx.annotation.TxTransaction;
import com.itmyiedu.api.PayCallBackService;
import com.itmyiedu.base.BaseApiService;
import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.constants.Constants;
import com.itmyiedu.entity.PaymentInfo;
import com.itmyiedu.feign.OrderFeign;
import com.itmyiedu.mapper.PaymentInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2018/11/13.
 */
@Slf4j
@RestController
public class PayCallBackServiceImpl extends BaseApiService implements PayCallBackService {
    @Autowired
    private PaymentInfoDao paymentInfoDao;
    @Autowired
    private OrderFeign orderFeign;
    @Override
    public ResponseBase synCallBack(@RequestParam Map<String,String> params) {
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            if(!signVerified){
                return setResultError("验签失败");
            }
            String out_trade_no=params.get("out_trade_no");
            String trade_no=params.get("trade_no");
            String total_amount=params.get("total_amount");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("out_trade_no", out_trade_no);
            jsonObject.put("trade_no", trade_no);
            jsonObject.put("total_amount", total_amount);
            return setResultSuccess(jsonObject);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return setResultError("系统错误");
        }
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional
    public String asynCallBack(@RequestParam Map<String,String> params) {
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            if(!signVerified){
                return Constants.HTTP_RES_CODE_500_VALUE;
            }
            // 商户订单号
            String out_trade_no=params.get("out_trade_no");
            PaymentInfo paymentInfo = paymentInfoDao.getByOrderIdPayInfo(out_trade_no);
            if(paymentInfo==null){
                //事物手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Constants.HTTP_RES_CODE_500_VALUE;
            }
            //支付宝重试机制，保证幂等性
            if(paymentInfo.getState()!=0){
                return Constants.HTTP_RES_CODE_200_VALUE;
            }
            String trade_no=params.get("trade_no");
            //支付宝实收金额
            String total_amount=params.get("total_amount");
            if(paymentInfo.getPrice().intValue()!=(int)Float.parseFloat(total_amount)){
                return Constants.HTTP_RES_CODE_500_VALUE;
            }
            paymentInfo.setState(1);
            paymentInfo.setPayMessage(params.toString());
            paymentInfo.setPlatformorderId(trade_no);
            int execute_result=paymentInfoDao.updatePayInfo(paymentInfo);
            if(execute_result<=0){
                return Constants.HTTP_RES_CODE_500_VALUE;
            }
            ResponseBase responseBase = orderFeign.updateOrderIdInfo((long) 1, trade_no, out_trade_no);
            if(!responseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)){
                return Constants.HTTP_RES_CODE_500_VALUE;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return Constants.HTTP_RES_CODE_500_VALUE;
        }
        return Constants.HTTP_RES_CODE_200_VALUE;
    }
}
