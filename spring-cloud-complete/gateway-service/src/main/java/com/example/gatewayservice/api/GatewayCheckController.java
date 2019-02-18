package com.example.gatewayservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class GatewayCheckController {

	@Autowired
	private Environment environment;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String up(){
		return "Gateway application status - " + environment.getProperty("app.status") + "<br>" 
				+ "Auth Secret: " + environment.getProperty("auth.secret");
	}
}
