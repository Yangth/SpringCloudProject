package com.itmyiedu.email.service;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.itmyiedu.adapter.MessageAdapter;

import lombok.extern.slf4j.Slf4j;

//处理第三方发送邮件
@Slf4j
@Service
public class EmailService implements MessageAdapter {

	@Value("${msg.subject}")
	private String subject;
	@Value("${msg.text}")
	private String text;
	@Value("${spring.mail.username}")
	private String MailFrom;
	@Autowired
    private  JavaMailSender  javaMailSender;
	@Override
	public void sendMsg(JSONObject body) {
		// 处理发送邮件
		String email = body.getString("email");
		if (StringUtils.isEmpty(email)) {
			return;
		}
		log.info("消息服务平台发送邮件:{}开始", email);
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		// 来自账号 自己发自己
		simpleMailMessage.setFrom(MailFrom);
		// 发送账号
		simpleMailMessage.setTo(email);
		// 标题
		simpleMailMessage.setSubject(subject);
		// 内容
		simpleMailMessage.setText(String.format(text, email));
		// 发送邮件
		javaMailSender.send(simpleMailMessage);
		log.info("消息服务平台发送邮件:{}完成", email);
	}

}
