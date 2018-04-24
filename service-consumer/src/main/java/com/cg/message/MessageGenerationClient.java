package com.cg.message;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient("https://service-pcf")
public interface MessageGenerationClient {

	//@RequestMapping(method=RequestMethod.GET, value="/restaurant")
	//String greeting();
}
