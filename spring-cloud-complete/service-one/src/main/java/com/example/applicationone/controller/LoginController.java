package com.example.applicationone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(value="/error", method=RequestMethod.GET)
	public String error(){
		return "Failed to login";
	}
	
	@RequestMapping(value="/success", method=RequestMethod.GET)
	public String success(){
		return "Login successful";
	}
}
