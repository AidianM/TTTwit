package com.tts.techtalenttwiter.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;
	
	@Email(message = "Please provide a valid email")
	@NotEmpty(message = "Please provide an email address")
	private String email;
	
	@Length(min=3, message="User name must have at least 3 characters.")
	@Length(max=15, message="User name cannot have more than 15 characters.")
	@Pattern(regexp="[^\\s]+", message="User name cannot contain spaces.")
	private String username;
	
	@Length(min=5, message="Your password must have at least 5 characters.")
	private String password;
	
	@NotEmpty(message="Please provide your first name.")
	private String firstName;
	
	@NotEmpty(message="Please provide your last name.")
	private String lastName;
	
	
	//adding follower information (w4d5/Day 2 slides)
	//can't just store a list, must make mapping, so we go Many:Many
	//but what will users be related to? Other users. We'll have a Many:Many mapping of UserProfile:UserProfile
	//A "User" has many "Followers"
	//A "User" has many people they're "Following" - it's reciprocal many:many
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_follower", joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name="follower_id"))
	private List<UserProfile> followers;
	
	@ManyToMany(mappedBy="followers")
	private List<UserProfile> following; //inverse just calls the prior mappedBy
	
	private int active;
	
	@CreationTimestamp
	private Date createdAt;
	
	
	//We need a constructor - no argument, in order for Entities to work
	//We need a constructor to actually set up everything
	
	//We need getters/setters
	//This is getting lengthy. This is where Lombok comes in (@NoArgsConstructor/@AllArgsConstructor)
	//Also @Data - Lombok to auto-handle fields/get/set/toString/etc
	
	//Saves ~80 lines of code from manual creation.
	
	//ROLES:
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles;
	
		//set makes, effectively, a list that holds unordered unique items (no two the same); Role is another class
		
		//A normal database column can't hold multiple pieces of info, so our db with |user_id, email, ..., createdAt| will NOT be storing 
		//the collection of Roles. Instead, Roles will be associated with user through a database relationship.
		
		//We're going to make this relationship be a many:many between roles:users (ie a user can have many roles, and each role can have 
		//many users associated with it)
		//In order to have a many:many database relationship, we create a separate table that lists what roles and users are associated with 
		//each other
		
		//Table: user_role
		//columns: user_id, role_id (both foreign keys)
		//			  1		   1 	(user_id 1 has role_id 1)
		
		//@ManyToMany will create the table efficiently, but doesn't allow for formatting, so we add it parenthetically, as above
		
	
}
