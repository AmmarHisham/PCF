package com.cg.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cg.service.RabbitMQReceiver;

@RestController
public class WebController {
	
	@Autowired
	RabbitMQReceiver receiver;
	
	@RequestMapping("/receiveAllMsg")
	public ArrayList<String> getAllMessage(){
		ArrayList<String> messList =  receiver.getAllMessage();
		return messList;
	}
}
