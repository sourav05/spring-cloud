package com.example.gatewayservice;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.gatewayservice.security.SecurityProperties;

@Configuration
@RefreshScope
public class Configurations {

	@Bean
	public SecurityProperties securityProperties(){
		return new SecurityProperties();
	}
}
