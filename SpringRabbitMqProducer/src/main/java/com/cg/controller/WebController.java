package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cg.service.RabbitMQSender;

@RestController
public class WebController {
	
	@Autowired
	RabbitMQSender sender;
	
	@RequestMapping("/send")
	public String sendMsg(@RequestParam("msg")String message){
		sender.sendMessage(message);
		return "Done";
	}
}
