package com.tts.techtalenttwiter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tts.techtalenttwiter.model.Tag;
import com.tts.techtalenttwiter.model.Tweet;
import com.tts.techtalenttwiter.model.TweetDisplay;
import com.tts.techtalenttwiter.model.UserProfile;
import com.tts.techtalenttwiter.repository.TagRepository;
import com.tts.techtalenttwiter.repository.TweetRepository;

@Service
public class TweetService {

	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	public List<TweetDisplay> findAll(){
		List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
		return formatTweets(tweets);
	}
	
	public List<TweetDisplay> findAllByUser(UserProfile user){
		List<Tweet> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
		return formatTweets(tweets);
	}
	
	public List<TweetDisplay> findAllByUsers(List<UserProfile> users){
		List<Tweet> tweets = tweetRepository.findAllByUserInOrderByCreatedAtDesc(users);
		return formatTweets(tweets);
	}
	
	//
	
	public void save(Tweet tweet) {
		handleTags(tweet);
		tweetRepository.save(tweet);
	}
	
	//adding hashtags:
	public void handleTags(Tweet tweet) {
		List<Tag> tags = new ArrayList<Tag>();
		
		//REGEX ASCENDANT!
		Pattern pattern = Pattern.compile("#\\w+"); //anything 1+ chars !whitespace
		Matcher matcher = pattern.matcher(tweet.getMessage());
		
		while(matcher.find()) {
			String phrase = matcher.group().substring(1).toLowerCase(); //substring 1 get everything past the #
			Tag tag = tagRepository.findByPhrase(phrase);
			
			if (tag == null) {
				//tag not found, save new tag:
				tag = new Tag();
				tag.setPhrase(phrase);
				tagRepository.save(tag);
			}
			//tag exists? Add to tag
			tags.add(tag);
		}
		tweet.setTags(tags);
		//Many:Many updates associations so the correct build betwixt tweets 'n' tags attach
	}
	
	private List<TweetDisplay> formatTweets(List<Tweet> tweets){
		//add tags to tweets:
		addTagLinks(tweets);
		//shorten url:
		shortenLinks(tweets);
		List<TweetDisplay> displayTweets = formatTimestamps(tweets);
		return displayTweets;
	}
	
	private void addTagLinks(List<Tweet> tweets) {
		Pattern pattern = Pattern.compile("#\\w+");
		for (Tweet tweet: tweets) {
			String message = tweet.getMessage();
			Matcher matcher = pattern.matcher(message);
			Set<String> tags = new HashSet<String>(); //like hashmap, but only hold keys, not key:value
			
			//while matcher finds a match, add to group:
			while (matcher.find()) {
				tags.add(matcher.group());
			}
			
			for (String tag:tags) {
				message = message.replaceAll(tag, 
						"<a class=\"tag\" href=\"/tweets/" +tag.substring(1).toLowerCase() + "\">"
						+tag+"</a>"
						);
			}
			tweet.setMessage(message);
		}
	}
	
	//finding with tags:
	public List<TweetDisplay> findAllWithTag(String tag){
		List<Tweet> tweets = tweetRepository.findByTags_PhraseOrderByCreatedAtDesc(tag);
		return formatTweets(tweets);
	}
	
	//link shortening:
	private void shortenLinks(List<Tweet> tweets) {
		Pattern pattern = Pattern.compile("https?[^ ]+");
		for (Tweet tweet: tweets) {
			String message = tweet.getMessage();
			Matcher matcher = pattern.matcher(message);
			while (matcher.find()) {
				String link = matcher.group();
				String shortenedLink = link;
				if (link.length() >23) {
					shortenedLink = link.substring(0, 20)+"...";
					message = message.replace(link, 
										"<a class=\"tag\" href=\""+link+"\" target=\"_blank\">"
										+shortenedLink
										+"</a>");
				}
			}
			tweet.setMessage(message);
		}
	}
	
	private List<TweetDisplay> formatTimestamps(List<Tweet> tweets){
		List<TweetDisplay> response = new ArrayList<>();
		PrettyTime prettyTime = new PrettyTime();
		SimpleDateFormat simpleDate = new SimpleDateFormat("M/d/yy");
		
		Date now = new Date();
		
		for(Tweet tweet: tweets) {
			TweetDisplay tweetDisplay = new TweetDisplay();
			tweetDisplay.setUser(tweet.getUser());
			tweetDisplay.setMessage(tweet.getMessage());
			tweetDisplay.setTags(tweet.getTags());
			//standard copy, and then comes the prettified date formats:
			
			long diffInMillis = Math.abs(now.getTime() - tweet.getCreatedAt().getTime());
			long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
			
			if(diffInDays>3) {
				tweetDisplay.setDate(simpleDate.format(tweet.getCreatedAt()));
			} else {
				tweetDisplay.setDate(prettyTime.format(tweet.getCreatedAt()));
			}
			response.add(tweetDisplay);
		}
		
		return response;
		
	}

























}
