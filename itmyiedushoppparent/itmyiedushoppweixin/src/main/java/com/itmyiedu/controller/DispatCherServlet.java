package com.itmyiedu.controller;

import com.alibaba.fastjson.JSONObject;
import com.itmyiedu.base.TextMessage;
import com.itmyiedu.utils.CheckUtil;
import com.itmyiedu.utils.HttpClientUtil;
import com.itmyiedu.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class DispatCherServlet {
    private static final String REQEST_HTTP = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";

	@RequestMapping(value = "/dispatCherServlet", method = RequestMethod.GET)
	public String dispatCherServletGet(String signature, String timestamp, String nonce, String echostr) {
		// 1.验证是否微信来源
		boolean checkSignature = CheckUtil.checkSignature(signature, timestamp, nonce);
		// 2.如果是微信来源 返回 随机数echostr
		if (!checkSignature) {
			return null;
		}
		return echostr;
	}

	@RequestMapping(value = "/dispatCherServlet", method = RequestMethod.POST)
	public void dispatCherServletPost(HttpServletRequest reqest, HttpServletResponse response) throws Exception {
		reqest.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, String> mapResult = XmlUtils.parseXml(reqest);
		if (mapResult == null) {
			return;
		}
		String msgType = mapResult.get("MsgType");
		PrintWriter writer = response.getWriter();
		switch (msgType) {
		case "text":

			// 获取消息内容
			String content = mapResult.get("Content");
			// 发送消息
			String toUserName = mapResult.get("ToUserName");
			// 来自消息
			String fromUserName = mapResult.get("FromUserName");
			// 調用智能机器人接口
			String requestResultJson = HttpClientUtil.doGet(REQEST_HTTP + content);
			JSONObject jsonObject = new JSONObject().parseObject(requestResultJson);
			String result = jsonObject.getString("result");
			String msg = null;
			if (result.equals("0")) {
				msg = jsonObject.getString("content");
			} else {
				msg = "我也不知道回答什么！";
			}
			String resultTestMsg = setTextMess(msg, toUserName, fromUserName);
			writer.print(resultTestMsg);
			break;

		default:
			break;
		}
		writer.close();

	}

	public String setTextMess(String content, String fromUserName, String toUserName) {
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(fromUserName);
		textMessage.setToUserName(toUserName);
		textMessage.setContent(content);
		textMessage.setMsgType("text");
		textMessage.setCreateTime(new Date().getTime());
		String messageToXml = XmlUtils.messageToXml(textMessage);
		log.info("####setTextMess()###messageToXml:" + messageToXml);
		return messageToXml;
	}

}
