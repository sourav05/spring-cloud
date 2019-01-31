package com.example.applicationone.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.applicationone.model.User;

@Repository
public interface ServiceOneRepository extends CrudRepository<User, Long>{

}
