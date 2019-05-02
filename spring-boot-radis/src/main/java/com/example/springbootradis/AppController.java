package com.example.springbootradis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

	@Autowired
	private RedisApplicationService dao;
	
	@GetMapping("/add")
	public void addStudent(){
		dao.addStudent();
	}

	public RedisApplicationService getDao() {
		return dao;
	}

	public void setDao(RedisApplicationService dao) {
		this.dao = dao;
	}
}
