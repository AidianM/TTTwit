package com.tts.techtalenttwiter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.techtalenttwiter.model.UserProfile;
import com.tts.techtalenttwiter.service.UserService;

@Controller
public class AuthorizationController {

	@Autowired
	UserService userService;
	
	@GetMapping(value="/login")
	public String login(){
		return "login";
	}
	
	@GetMapping(value="/signup")
	public String registration(Model model) {
		UserProfile user = new UserProfile();
		model.addAttribute("userProfile", user);
		return "registration";
	}
	
	@PostMapping(value="/signup")
	public String createNewUser(@Valid UserProfile userProfile, 
								BindingResult bindingResult,
								Model model) {
		//Check to see if User exists:
		//no access to UserService, must inject
		UserProfile userExists = userService.findByUsername(userProfile.getUsername());
		
		if (userExists != null) {
			bindingResult.rejectValue("username", "error: user", "User name is already taken."); //value for error, error result, error notation display
		}
		if(!bindingResult.hasErrors()) {
			//userService returns user
			userService.saveNewUser(userProfile);
			model.addAttribute("Success", "Signup successful!");
			model.addAttribute("userProfile", new UserProfile()); //need to return blank new User for formatting

//		} else {
//			UserProfile errorUser = new UserProfile();
//			errorUser.setFirstName("Error");
//			model.addAttribute("user", errorUser); //need to return blank new User for formatting
		}

		return "registration";
	}
}
