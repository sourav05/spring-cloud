package com.example.gatewayservice.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;

@RefreshScope
public class SecurityProperties {
	
	@Autowired
	private Environment environment;
	
	private static String AUTH_SECRET;
	
	public SecurityProperties() {
		System.out.println();
	}
	
	@PostConstruct
	private void init(){
		AUTH_SECRET = environment.getProperty("auth.secret");
	}
	
	public String getAuthSecret(){
		return AUTH_SECRET;
	}
}
