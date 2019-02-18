package com.example.gatewayservice.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GatewayAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	public GatewayAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
													HttpServletResponse response) throws AuthenticationException {
		SecurityUser user;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), SecurityUser.class);
			if(user != null){
				Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities()));
				return authentication;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request,
										 HttpServletResponse response,
										 FilterChain chain,
										 Authentication auth) throws AuthenticationException {
		String token = JWT.create().withSubject(((User)auth.getPrincipal()).getUsername())
		.withExpiresAt(new Date(System.currentTimeMillis() + 30000000))
		.sign(Algorithm.HMAC256(new SecurityProperties().getAuthSecret().getBytes()));
		
		String authHeader = "Bearer " + token;
		response.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
	}
}
