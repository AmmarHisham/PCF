package webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractController {
	
	@RequestMapping(value="/")
	public String homePage(){
		return "Welcome to Programmatic Microservices!!";
	}
	
}
