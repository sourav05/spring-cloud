package com.example.applicationtwo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class ServiceTwoAuthorizationFilter extends BasicAuthenticationFilter {

	private String TOKEN_SECRET = "dc923ernw-1";
	
	public ServiceTwoAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	public void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException{
		String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION.toString());
		if(authHeader == null || authHeader.equalsIgnoreCase("")){
			chain.doFilter(req, resp);
			return;
		}
		String user = JWT.require(Algorithm.HMAC384(TOKEN_SECRET.getBytes())).build().verify(authHeader.replace("Bearer ", "")).getSubject();
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<SimpleGrantedAuthority>());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, resp);
	}

}
