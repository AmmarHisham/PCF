package com.cg.service;

import java.util.ArrayList;

public interface RabbitMQReceiver {

	public void reveiveMessage(String message);

	public ArrayList<String> getAllMessage();

}
