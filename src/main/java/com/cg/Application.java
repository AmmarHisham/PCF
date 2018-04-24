package com.cg;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cg.service.ApplicationService;



@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class Application {

	@Autowired
	ApplicationService applicationService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {

		String greeting = applicationService.getGreetings();

		return greeting;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		
		return "Consumer service is accessible";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
