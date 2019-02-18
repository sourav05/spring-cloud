package com.example.applicationtwo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.applicationtwo.model.User;
import com.example.applicationtwo.service.ServiceTwo;

@RestController
@RequestMapping(value="/service-two")
public class ServiceTwoController {

	@Autowired
	private ServiceTwo serviceTwo;
	
	@RequestMapping(value="/get", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> users = serviceTwo.getAllUser();
		HttpHeaders headers = new HttpHeaders();
		headers.add("server-name", "service-two");
		return ResponseEntity.ok().headers(headers).body(users);
	}
	
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<User> getUser(@PathVariable(value="id") long id){
		User user = serviceTwo.getUser(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("server-name", "service-two");
		return ResponseEntity.ok().headers(headers).body(user);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		user = serviceTwo.saveUser(user);
		HttpHeaders headers = new HttpHeaders();
		headers.add("server-name", "service-two");
		return ResponseEntity.ok().headers(headers).body(user);
	}
}
