package com.example.applicationtwo.security;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.applicationtwo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceTwoAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private String TOKEN_SECRET = "dc923ernw-1";
	private AuthenticationManager authenticationManager;
	
	public ServiceTwoAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse respo) throws AuthenticationException{
		User user;
		try {
			user = new ObjectMapper().readValue(req.getInputStream(), User.class);
			if(user != null){
				Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
				return auth;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication auth) throws AuthenticationException {
		String token = JWT.create().withSubject(((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 300000)).sign(Algorithm.HMAC384(TOKEN_SECRET.getBytes()));
		
		String headerToken = "Bearer " + token;
		resp.setHeader(HttpHeaders.AUTHORIZATION, headerToken);
	}
}
