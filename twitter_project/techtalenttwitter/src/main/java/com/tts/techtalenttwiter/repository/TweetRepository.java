package com.tts.techtalenttwiter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.techtalenttwiter.model.Tweet;
import com.tts.techtalenttwiter.model.UserProfile;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long>{
	
	List<Tweet> findAllByOrderByCreatedAtDesc(); //display
	List<Tweet> findAllByUserOrderByCreatedAtDesc(UserProfile user); //show by user 
	List<Tweet> findAllByUserInOrderByCreatedAtDesc(List<UserProfile> users); //all tweets any user in list has provided
	List<Tweet> findByTags_PhraseOrderByCreatedAtDesc(String phrase);//find by phrase with tags inside

}
