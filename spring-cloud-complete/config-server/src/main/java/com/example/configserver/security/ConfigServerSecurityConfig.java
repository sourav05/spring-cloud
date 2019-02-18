package com.example.configserver.security;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class ConfigServerSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.passwordEncoder(new BCryptPasswordEncoder())
				.withUser("sourav").password("password")
				.authorities(new ArrayList<GrantedAuthority>());
	}
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.csrf().disable()
			.httpBasic();;
	}
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("password"));
		System.out.println(new BCryptPasswordEncoder().matches("password", "$2a$10$A2wlbqP2AA5qcdh3IVQ0OOtgD5iIwGLeRHYTqNuodQubGqnsQi7i6"));
	}
}
