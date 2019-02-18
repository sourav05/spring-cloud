package com.example.applicationone.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.applicationone.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource datasource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//Below code is for in memory user details
		/*auth
			.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder())
				.withUser("sourav")
				.password(bCryptPasswordEncoder().encode("pass1"))
				.roles("USER")
			.and()
				.withUser("someone")
				.password(bCryptPasswordEncoder().encode("pass2"))
				.roles("OTHER");*/
		
		//Below code is for custom user details service
		auth.userDetailsService(userDetailsService())/*.passwordEncoder(bCryptPasswordEncoder())*/;
		
		//below code is for jdbc credentials
		/*auth
			.jdbcAuthentication()
			.dataSource(datasource)
			.usersByUsernameQuery("Select name, 'password1', true from user_tbl where name=?")
			.authoritiesByUsernameQuery("select name, 'USER' from user_tbl where name=?")
			.passwordEncoder(bCryptPasswordEncoder());*/
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
				.and()
					.csrf()
						.disable()
			.authorizeRequests()
					.antMatchers(HttpMethod.POST).permitAll()
					.anyRequest().authenticated()
					.and()
						.formLogin()
						.failureUrl("/login")
					.and()
						//.addFilter(new ServiceOneAuthenticationFilter(authenticationManager()))
						.addFilter(new ServiceOneAuthorizationFilter(authenticationManager()))
					.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
//				;
		/*http
		.cors().and().csrf()
			.disable()
		.authorizeRequests()
			.antMatchers(HttpMethod.POST).permitAll()
			.anyRequest().authenticated()
		.and()
			.addFilter(new ServiceOneAuthenticationFilter(authenticationManager()))
			.addFilter(new ServiceOneAuthorizationFilter(authenticationManager()))
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(){
		return new UserDetailsServiceImpl();
	}
}
