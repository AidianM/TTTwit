package com.tts.techtalenttwiter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tweet {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tweet_id")
	private Long id;
	
	@NotEmpty(message = "Tweet cannot be empty")
	@Length(max = 200, message = "Tweet cannot have more than 280 characters.")
	private String message;
	
	
	//add hashtag list:
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
	@JoinTable(name= "tweet_tag", joinColumns=@JoinColumn(name="tweet_id"), 
	inverseJoinColumns = @JoinColumn(name="tag_id"))
	private List<Tag> tags;
	
	@CreationTimestamp
	private Date createdAt;
	
	
	//To get the posting user display:
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "user_id") //tie to UserProfile.java @Column(name)
	@OnDelete(action = OnDeleteAction.CASCADE) // delete user == delete all
	private UserProfile user;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
