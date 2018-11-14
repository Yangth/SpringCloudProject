package com.itmyiedu.mq;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class RegisterMailboxProducer {
	@Autowired
	private JmsTemplate jmsMessagingTemplate;

	public void sendMsg(Destination destination, String json) {
		jmsMessagingTemplate.convertAndSend(destination, json);
	}

}
