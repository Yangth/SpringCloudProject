package com.itmyiedu.api;

import com.itmyiedu.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/order")
public interface OrderService {

	@RequestMapping("/updateOrderIdInfo")
	ResponseBase updateOrderIdInfo(@RequestParam("isPay") Long isPay, @RequestParam("payId") String aliPayId,
								   @RequestParam("orderNumber") String orderNumber);

	@RequestMapping("/insertOrderInfo")
	ResponseBase insertOrderInfo(@RequestParam("orderNumber") String orderNumber,@RequestParam("userId") String userId);
}
