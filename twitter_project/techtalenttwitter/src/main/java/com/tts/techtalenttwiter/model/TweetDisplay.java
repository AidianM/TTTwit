package com.tts.techtalenttwiter.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//display model only, no @Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetDisplay {

	//basically just hold a copy of the tweet with a different message - 
	private UserProfile user;
	private String message;
	private String date;
	private List<Tag> tags;
}
