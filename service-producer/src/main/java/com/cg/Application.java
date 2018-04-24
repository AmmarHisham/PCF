package com.cg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class Application {

    @RequestMapping("/produce")
    public String produce() {
      return String.format("Message from producer service");
    }
    
    @RequestMapping("/test")
    public String test() {
      return String.format("Producer service is accessible");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
