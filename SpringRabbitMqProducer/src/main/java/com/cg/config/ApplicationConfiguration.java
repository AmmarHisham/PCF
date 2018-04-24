package com.cg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.AmqpTemplate;

@Configuration
public class ApplicationConfiguration extends AbstractCloudConfig {
	
	@Value("${rmq.host}")
	private String rabbitHost;

	@Value("${rmq.port}")
	private int rabbitPort;

	@Value("${rmq.username}")
	private String rabbitUserName;

	@Value("${rmq.password}")
	private String rabbitPassword;

	@Value("${rmq.vhost}")
	private String rabbitVirtualHost;
	
	
	@Bean(name = "amqpSource")
	public ConnectionFactory rabbitConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(rabbitHost);
		connectionFactory.setPort(rabbitPort);
		connectionFactory.setUsername(rabbitUserName);
		connectionFactory.setPassword(rabbitPassword);
		connectionFactory.setVirtualHost(rabbitVirtualHost);
		return connectionFactory;
	}
	
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean(name = "rabbitTemplateForMessage")
	public AmqpTemplate rabbitTemplateForMessage() {
		return new RabbitTemplate(rabbitConnectionFactory());
	}

	
	

}
