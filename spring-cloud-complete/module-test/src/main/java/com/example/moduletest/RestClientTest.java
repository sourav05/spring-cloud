package com.example.moduletest;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.applicationone.model.User;
import com.example.applicationone.model.UserEntity;

public class RestClientTest {

	public static void main(String[] args) {
		new RestClientTest().addUser();
//		new RestClientTest().getUser();
		
//		new RestClientTest().register();
	}

	public void addUser(){
		for(int i=0;i<10;i++){
		RestTemplate template = new RestTemplate();
		User user = new User();
		user.setUsername("sourav" + "_" + i);
		user.setPassword("password" + "_" + i);
		user.setUsername("NameOne" + "_" + i);
		user.setOrganisation("OrganizationOne" + "_" + i);
		user.setLocation("LocationOne" + "_" + i);
		user.setAge(30);

		HttpEntity<User> entity = new HttpEntity<User>(user);
		ResponseEntity<User> responseEntity1 = template.exchange("http://localhost:8091/service-one/save", HttpMethod.POST, entity, User.class);
		User receivedUser = responseEntity1.getBody();
		System.out.println("New USer ID: " + receivedUser.getId());
		HttpHeaders header = responseEntity1.getHeaders();
		System.out.println("Server-name : " + header.get("server-name"));
		System.out.println("Status Code: " + responseEntity1.getStatusCodeValue() + " : " + responseEntity1.getStatusCode().toString());
		}
		//		}
	}

	public void getUser(){
		RestTemplate template = new RestTemplate();
		User user = new User();
		user.setUsername("NameOne");
		user.setOrganisation("OrganizationOne");
		user.setLocation("LocationOne");
		user.setAge(30);
//		HttpEntity<User> entity = new HttpEntity<User>(user/*, getAuthHeader()*/);
//		ResponseEntity<User> responseEntity = template.exchange("http://localhost:8083/service-one/service-one/save", HttpMethod.POST, entity, User.class);
		String authHeader = "";
//		if(responseEntity.getStatusCodeValue() != 200){
		SecurityUser sUser = new SecurityUser();
		sUser.setUsername("sourav");
		sUser.setPassword("password");
		sUser.setEnabled(true);
		ResponseEntity<HttpServletResponse> response = template.exchange("http://localhost:8083/login", HttpMethod.POST, new HttpEntity<>(sUser), HttpServletResponse.class);
		authHeader = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.AUTHORIZATION, authHeader);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add(HttpHeaders.AUTHORIZATION, authHeader);
		ResponseEntity<User[]> usersObject = template.exchange("http://localhost:8083/service-one/service-one/get", 
				HttpMethod.GET, new HttpEntity<>(map), User[].class);
		Stream.of(usersObject.getBody()).forEach(e -> {
			System.out.println("User Id : " + e.getId());
			System.out.println("User Name : " + e.getUsername());
		});
		HttpHeaders headers = usersObject.getHeaders();
		System.out.println("Server-name : " + headers.get("server-name"));
	}

	private HttpHeaders getAuthHeader(){
		return new HttpHeaders() {
			private static final long serialVersionUID = 1L;
		{
			String authUP = "user:6a530b79-65eb-4826-b97c-08daee36d2f5";
			byte[] encodedAuth = Base64.getEncoder().encode(authUP.getBytes(Charset.forName("US-ASCII")));
			String authHeaderVal = "Basic " + encodedAuth;
			set(HttpHeaders.AUTHORIZATION, authHeaderVal);
		}};
	}

	public void authorize(){
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		UserEntity uEntity = new UserEntity();
		uEntity.setUsername("sourav");
		uEntity.setPassword("password");
		uEntity.setActive(true);

		HttpEntity<UserEntity> entity = new HttpEntity<>(uEntity);
		ResponseEntity<HttpServletResponse> resp = template.exchange("http://127.0.0.1:9876/login",HttpMethod.POST, 
														entity, HttpServletResponse.class);
		System.out.println(resp.getHeaders().getFirst(HttpHeaders.AUTHORIZATION.toString()));
	}
	
	public void register(){
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		UserEntity uEntity = new UserEntity();
		uEntity.setUsername("sourav");
		uEntity.setPassword("password");
		uEntity.setActive(true);

		HttpEntity<UserEntity> entity = new HttpEntity<>(uEntity);
		ResponseEntity<UserEntity> resp = template.exchange("http://127.0.0.1:9876/user/register",HttpMethod.POST, 
											entity, UserEntity.class);
		System.out.println(resp.getHeaders().getFirst(HttpHeaders.AUTHORIZATION.toString()));
	}
}
