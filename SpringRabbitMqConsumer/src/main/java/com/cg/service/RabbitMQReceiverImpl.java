package com.cg.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RabbitMQReceiverImpl implements RabbitMQReceiver {

	private Logger logger = LoggerFactory.getLogger(RabbitMQReceiverImpl.class);

	public static ArrayList<String> msgArr = new ArrayList<String>();

	@Autowired
	AmqpTemplate rabbitTemplate;

	@Value("${rmq.exchangeOrder}")
	private String messageExchange;

	@Value("${rmq.routingkeyOrder}")
	private String messageRoutingKey;

	@RabbitListener(queues = "${rmq.queue}")
	public void reveiveMessage(String message) {

		try {
			logger.info("Message reveived successfully " + message);
			msgArr.add(message);
			rabbitTemplate.convertAndSend(messageExchange, messageRoutingKey, message);

		} catch (Exception ex) {
			logger.error("Exception Occured" + ex.getMessage());
		}

	}

	@Override
	public ArrayList<String> getAllMessage() {
		return msgArr;
	}

}
