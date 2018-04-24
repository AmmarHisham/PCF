package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate rest;
	
	
	
	@HystrixCommand(fallbackMethod = "getBackup")
	public String getGreetings() {
		URI uri = UriComponentsBuilder.fromUriString("//SERVICE-PRODUCER/produce").build().toUri();

		String greeting = rest.getForObject(uri, String.class);

		return greeting;
	}
	
	public String getBackup() {
		return "Service is currently not available please try again later";
	}
	
}
