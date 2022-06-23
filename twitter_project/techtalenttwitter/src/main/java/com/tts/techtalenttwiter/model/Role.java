package com.tts.techtalenttwiter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Stored to db, so add @Entity
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id") //match with User.java for relationship
	private Long id;

	private String role;
	
	//@Data et al Lombok constructors added atop
	
	//Role class now complete.
}
