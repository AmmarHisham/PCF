package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;

@Service
public class RabbitMQSenderImpl implements RabbitMQSender {

	
	@Autowired
	@Qualifier("rabbitTemplateForMessage")
	AmqpTemplate rabbitTemplate;
	
	@Value("${rmq.exchange}")
	private String messageExchange;

	@Value("${rmq.routingkey}")
	private String messageRoutingKey;
	
	@Override
	public void sendMessage(String message) {
		rabbitTemplate.convertAndSend(messageExchange, messageRoutingKey,message);
		
	}

}
