package com.example.applicationtwo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class ServiceTwoAppConfig {

	@Bean
	public SCryptPasswordEncoder sCryptPasswordEncoder(){
		return new SCryptPasswordEncoder();
	}
}
