package com.example.applicationone;

import java.util.stream.Stream;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.applicationone.model.User;

public class RestClientTest {

	public static void main(String[] args) {
//		new RestClientTest().addUser();
		new RestClientTest().getUser();
	}
	
	public void addUser(){
		RestTemplate template = new RestTemplate();
		User user = new User();
		user.setUsername("NameOne");
		user.setOrganisation("OrganizationOne");
		user.setLocation("LocationOne");
		user.setAge(30);
		HttpEntity<User> entity = new HttpEntity<User>(user);
		ResponseEntity<User> responseEntity = template.exchange("http://localhost:8080/service-one/save", HttpMethod.POST, entity, User.class);
		User receivedUser = responseEntity.getBody();
		System.out.println("New USer ID: " + receivedUser.getId());
	}
	
	public void getUser(){
		RestTemplate template = new RestTemplate();
		ResponseEntity<User[]> usersObject = template.getForEntity("http://localhost:8080/service-one/get", User[].class);
		Stream.of(usersObject.getBody()).forEach(e -> {
			System.out.println("User Id : " + e.getId());
			System.out.println("User Name : " + e.getUsername());
		});
	}

}
