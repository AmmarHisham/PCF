package com.cg.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	/**
	 * jsonMessageConverter Method
	 *
	 * @return MessageConverter
	 */
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	/**
	 * rabbitTemplateForMessage Method
	 *
	 * @param connectionFactory
	 * @return AmqpTemplate
	 */
	@Bean(name = "rabbitTemplateForMessage")
	public AmqpTemplate rabbitTemplateForMessage(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
		
	}

}
