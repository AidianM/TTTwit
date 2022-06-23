package com.tts.techtalenttwiter.service;

//import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tts.techtalenttwiter.model.Role;
import com.tts.techtalenttwiter.model.UserProfile;
import com.tts.techtalenttwiter.repository.RoleRepository;
import com.tts.techtalenttwiter.repository.UserRepository;


@Service
public class UserService {

//	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
	private RoleRepository roleRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//We can just inject via constructor:
	
	@Autowired
	public UserService(UserRepository userRepository,
						RoleRepository roleRepository,
						BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public UserProfile findByUsername (String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<UserProfile> findAll(){
		return userRepository.findAll();
	}
	
	public void save(UserProfile user) {
		userRepository.save(user);
	}
	
	public UserProfile saveNewUser(UserProfile user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //NEVER STORE UNENCRYPTED PASSWORDS
		user.setActive(1);
		Role userRole = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole))); //set roles via hash, updates table
		UserProfile newUser = userRepository.save(user); //save user to database, udpate roles et al; snag variable newUser for unique ID
		return newUser;
		
	}
	
	public UserProfile getLoggedInUser() {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName(); //standard pathway
		return findByUsername(loggedInUsername);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
