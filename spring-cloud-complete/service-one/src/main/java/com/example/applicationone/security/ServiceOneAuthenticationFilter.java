package com.example.applicationone.security;

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
import org.springframework.security.core.userdetails.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceOneAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	public static final String AUTH_SECRET = "auth_secret";
	
	public ServiceOneAuthenticationFilter(AuthenticationManager authenticationManager){
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException{
		try {
			com.example.applicationone.model.User user = new ObjectMapper().readValue(req.getInputStream(), com.example.applicationone.model.User.class);
			Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			return auth;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp,
											FilterChain chain, Authentication auth) throws AuthenticationException{
		
		String token = JWT.create().withSubject(((User)auth.getPrincipal()).getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 300000)).sign(Algorithm.HMAC256(AUTH_SECRET.getBytes()));
		String headerString = "Bearer " + token;
		
		resp.setHeader(HttpHeaders.AUTHORIZATION, headerString);
	}
}
