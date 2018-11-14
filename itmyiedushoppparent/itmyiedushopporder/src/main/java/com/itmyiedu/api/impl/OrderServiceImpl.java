package com.itmyiedu.api.impl;

import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.itmyiedu.api.OrderService;
import com.itmyiedu.base.BaseApiService;
import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.mapper.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderServiceImpl extends BaseApiService implements OrderService,ITxTransaction{
	@Autowired
	private OrderDao orderDao;

	@Override
	@Transactional
	public ResponseBase updateOrderIdInfo(@RequestParam("isPay") Long isPay, @RequestParam("payId") String aliPayId,
										  @RequestParam("orderNumber") String orderNumber) {
		int updateOrder = orderDao.updateOrder(isPay, aliPayId, orderNumber);
		if (updateOrder <= 0) {
			return setResultError("系统错误!");
		}
		return setResultSuccess();
	}

	@Override
	@Transactional
	public ResponseBase insertOrderInfo(String orderNumber, String userId) {
		int insertOrder = orderDao.insertOrder(orderNumber,userId);
		if (insertOrder <= 0) {
			return setResultError("系统错误!");
		}
		return setResultSuccess();
	}

}
