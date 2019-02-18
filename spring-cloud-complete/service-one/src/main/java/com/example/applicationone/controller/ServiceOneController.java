package com.example.applicationone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.applicationone.model.User;
import com.example.applicationone.service.ServiceOne;

@RestController
@RequestMapping(value="/service-one")
public class ServiceOneController {

	@Autowired
	private ServiceOne serviceOne;
	
	@RequestMapping(value="/get", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> users = serviceOne.getAllUser();
		HttpHeaders headers = new HttpHeaders();
		headers.add("server-name", "service-one");
		return ResponseEntity.ok().headers(headers).body(users);
	}
	
	@RequestMapping(value="/fetch", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<User>> fetchAllUser(){
		List<User> users = serviceOne.getAllUser();
		HttpHeaders headers = new HttpHeaders();
		headers.add("server-name", "service-one");
		return ResponseEntity.ok().headers(headers).body(users);
	}
	
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<User> getUser(@PathVariable(value="id") long id){
		User user = serviceOne.getUser(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("server-name", "service-one");
		return ResponseEntity.ok().headers(headers).body(user);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user = serviceOne.saveUser(user);
		HttpHeaders headers = new HttpHeaders();
		headers.add("server-name", "service-one");
		return ResponseEntity.ok().headers(headers).body(user);
	}
}
