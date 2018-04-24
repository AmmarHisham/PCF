package com.cg;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class Application {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate rest;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		URI uri = UriComponentsBuilder.fromUriString("//SERVICE-PRODUCER/produce").build().toUri();

		String greeting = rest.getForObject(uri, String.class);
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
