package com.example.applicationone.security;

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

public class ServiceOneAuthorizationFilter extends BasicAuthenticationFilter {

	public static final String AUTH_SECRET = "s@o$u%r*a$";
	
	public ServiceOneAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
		if(authHeader == null || authHeader.equalsIgnoreCase("")){
			chain.doFilter(req, resp);
			return;
		}
		UsernamePasswordAuthenticationToken auth = getAuthentication(authHeader);
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(req, resp);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String authHeader) {
		String user = JWT.require(Algorithm.HMAC256(AUTH_SECRET.getBytes())).build().verify(authHeader.replace("Bearer ", "")).getSubject();
		if(user != null && !user.equalsIgnoreCase(""))
			return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
		return null;
	}
}
