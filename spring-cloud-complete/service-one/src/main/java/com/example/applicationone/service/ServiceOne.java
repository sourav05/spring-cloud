package com.example.applicationone.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.applicationone.model.User;
import com.example.applicationone.repository.ServiceOneRepository;

@Service
public class ServiceOne {

	@Autowired
	private ServiceOneRepository serviceOneRepository;
	
	public List<User> getAllUser(){
		List<User> userList = new LinkedList<>();
		Iterable<User> iterable = serviceOneRepository.findAll();
		iterable.forEach(e -> {
			userList.add(e);
		});
		return userList;
	}
	
	public User getUser(long id){
		User user = serviceOneRepository.findById(id).orElse(new User());
		return user;
	}
	
	public User saveUser(User user){
		return serviceOneRepository.save(user);
	}
}
