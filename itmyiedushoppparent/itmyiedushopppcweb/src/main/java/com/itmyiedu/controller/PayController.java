package com.itmyiedu.controller;

import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.constants.Constants;
import com.itmyiedu.feign.PayServiceFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

@Controller
public class PayController {
    @Autowired
	private PayServiceFegin payServiceFegin;

	@RequestMapping("/pay")
	public void pay(String payToken, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html;charset=utf-8");
		ResponseBase payInfo = payServiceFegin.payInfo(payToken);
		PrintWriter out = resp.getWriter();
		if (!payInfo.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			out.println(payInfo.getMsg());
			return;
		}
		LinkedHashMap mapPayInfo = (LinkedHashMap) payInfo.getData();
		String payInfoHtml = (String) mapPayInfo.get("payInfo");
		out.println(payInfoHtml);
		out.close();
	}

}
