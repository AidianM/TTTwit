package com.tts.techtalenttwiter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.techtalenttwiter.model.UserProfile;


//We declare an interface rather than a class because WE aren't actually going to create the Repository; Spring Boot is.
//However, we need to specify to Spring Boot details on how we want our repository created, and what query methods
//	might be used on it, so we create an interface for Spring Boot to scan and analyze in order to create the 
//	real/implemented UserRepository.

//In order to this to be a repository, we have to inherit from Repository (or one of its subclasses).
//We never really inherit from Repository directly, but rather from one of its subclasses; in this case, from CrudRepository.

//CrudRepository takes two Generic types:
//	1. The type of object that will be stored in the repo
//	2. The type of object that is the primary key for type #1

@Repository
public interface UserRepository extends CrudRepository<UserProfile, Long>{
	
	//Inheriting from CrudRepository gives us a lot of methods already included (.save, findById, etc)
	//But we want to specify that one of hte queries we want to do is to look up a User by userName
	
	//The name of this method is parsed by SpringBoot and must conform to a specific patter
	UserProfile findByUsername(String username);
	
	@Override
	List<UserProfile> findAll();
}
