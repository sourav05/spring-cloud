package com.example.applicationtwo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class ServiceTwoSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.inMemoryAuthentication()
		.withUser("sourav")
		.password(passwordEncoder.encode("password"))
		.authorities("USER");
	}
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security
			.cors()
			.and()
			.csrf()
				.disable()
		.authorizeRequests()
			.antMatchers("/service-two/save").permitAll()
			.anyRequest().authenticated()
		.and()
			//.addFilter(new ServiceTwoAuthenticationFilter(authenticationManager()))
			.addFilter(new ServiceTwoAuthorizationFilter(authenticationManager()))
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
