package com.itmyiedu.manager;


import com.alipay.api.AlipayApiException;
import com.itmyiedu.entity.PaymentInfo;

public interface PayManager {

	 public String payInfo(PaymentInfo paymentInfo)  throws AlipayApiException, AlipayApiException;
	
}
