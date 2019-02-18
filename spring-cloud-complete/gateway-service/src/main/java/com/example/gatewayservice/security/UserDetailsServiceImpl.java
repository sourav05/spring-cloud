package com.example.gatewayservice.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(!username.equalsIgnoreCase("sourav"))
			throw new UsernameNotFoundException("User " + username + "doesn't exists");
		SecurityUser secUser = new SecurityUser();
		secUser.setUsername(username);
		secUser.setPassword(new BCryptPasswordEncoder().encode("password"));
		secUser.setEnabled(true);
		return new User(secUser.getUsername(), secUser.getPassword(), secUser.getAuthorities());
	}

}
