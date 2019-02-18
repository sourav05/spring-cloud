package com.example.gatewayservice.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class GatewayAuthorizationFilter extends BasicAuthenticationFilter {

	public GatewayAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	public void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
		if(authHeader == null || authHeader.equalsIgnoreCase("")){
			chain.doFilter(req, resp);
			return;
		}
		System.out.println("Auth Secret : " + new SecurityProperties().getAuthSecret());
		String username = JWT.require(Algorithm.HMAC256(new SecurityProperties().getAuthSecret().getBytes())).build().verify(authHeader.replace("Bearer ", "")).getSubject();
		if(username != null && !username.equalsIgnoreCase("")){
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(req, resp);
	}

}
