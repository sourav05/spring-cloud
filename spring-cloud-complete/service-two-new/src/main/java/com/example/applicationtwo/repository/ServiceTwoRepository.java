package com.example.applicationtwo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.applicationtwo.model.User;

@Repository
public interface ServiceTwoRepository extends CrudRepository<User, Long>{

}
