package com.cg.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Service
public class RabbitMQReceiverImpl implements RabbitMQReceiver {

	private Logger logger = LoggerFactory.getLogger(RabbitMQReceiverImpl.class);	
	
	public static ArrayList<String> msgArr = new ArrayList<String>();
	

	@RabbitListener(queues = "${rmq.queue}")
	public void reveiveMessage(String message) {

		try {
            	logger.info("Message reveived successfully "+message); 
            	msgArr.add(message);
		}
		catch(Exception ex) {
			logger.error("Exception Occured"+ex.getMessage());
		}
			
		}

	@Override
	public ArrayList<String> getAllMessage() {
		return msgArr;
	}
	
}

