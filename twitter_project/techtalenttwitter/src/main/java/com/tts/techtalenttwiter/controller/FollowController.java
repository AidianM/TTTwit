package com.tts.techtalenttwiter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.techtalenttwiter.model.UserProfile;
import com.tts.techtalenttwiter.service.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class FollowController {
	
	@Autowired
	private UserService userService;
	
	//to follow, we require the user makes a POST request to /follow/(username)
	
	@PostMapping(value="/follow/{username}")
	public String follow(@PathVariable(value="username") String username, 
						 HttpServletRequest request) {
		UserProfile loggedInUser = userService.getLoggedInUser();
		UserProfile userToFollow = userService.findByUsername(username);
		
		List<UserProfile> followers = userToFollow.getFollowers();
		followers.add(loggedInUser); //adds us to followers list
		userToFollow.setFollowers(followers); //update followers, probably not strictly necessary
		
		userService.save(userToFollow); //update database/inverse relationship
		
		//Referer is included automatically in post requests, just snag it to go back
		return "redirect:" + request.getHeader("Referer");
	}

	@PostMapping(value="/unfollow/{username}")
	public String unfollow(@PathVariable(value="username") String username, 
						 HttpServletRequest request) {
		UserProfile loggedInUser = userService.getLoggedInUser();
		UserProfile userToUnfollow = userService.findByUsername(username);
		
		List<UserProfile> followers = userToUnfollow.getFollowers();
		followers.remove(loggedInUser); //adds us to followers list
		userToUnfollow.setFollowers(followers); //update followers, probably not strictly necessary
		
		userService.save(userToUnfollow); //update database/inverse relationship
		
		//Referer is included automatically in post requests, just snag it to go back
		return "redirect:" + request.getHeader("Referer");
	}
	
}
