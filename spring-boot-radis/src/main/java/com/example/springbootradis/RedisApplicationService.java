package com.example.springbootradis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisApplicationService {

	@Autowired
	private RedisRepository redisRepository;
	
	public void addStudent(){
		Student s = new Student();
		s.setName("Aadrik");
		s.setAge(1);
		s.setGrade("LKG");
		
		Student s1 = redisRepository.save(s);
		System.out.println(s1.getId());
	}

}
