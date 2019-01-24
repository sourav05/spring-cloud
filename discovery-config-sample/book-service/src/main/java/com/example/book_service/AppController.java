package com.example.book_service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

	@RequestMapping(value="/name", method=RequestMethod.GET)
	public String getApplicationName(){
		return "App Name: book-service";
	}
}
