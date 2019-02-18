package com.example.applicationtwo.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.applicationtwo.model.User;
import com.example.applicationtwo.repository.ServiceTwoRepository;

@Service
public class ServiceTwo {

	@Autowired
	private ServiceTwoRepository serviceTwoRepository;
	
	public List<User> getAllUser(){
		List<User> userList = new LinkedList<>();
		Iterable<User> iterable = serviceTwoRepository.findAll();
		iterable.forEach(e -> {
			userList.add(e);
		});
		return userList;
	}
	
	public User getUser(long id){
		User user = serviceTwoRepository.findById(id).orElse(new User());
		return user;
	}
	
	public User saveUser(User user){
		return serviceTwoRepository.save(user);
	}
}
