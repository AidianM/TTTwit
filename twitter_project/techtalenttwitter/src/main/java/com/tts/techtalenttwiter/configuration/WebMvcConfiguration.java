package com.tts.techtalenttwiter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//If you mark a class with a configuration annotation, one of hte things it means is that youc an add instructions
//on how to make injectable (autowired) objects to Spring Boot

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	//The way we tell SB how to make an object that is to be Autowired is to use the @Bean annotation
	//A "Bean" in this context is an object whose lifecycle (Creation/Destruction) will not be done by the user calling constructors
	//	but will be managed by Spring Boot
	//The @Bean annotation tells Spring Boot how to create objects when they're needed.
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {//SpringBoot will scan all files to find the way to make passwordEncoder
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	//Allowing SB to manage your object via beans rather than instantiating them yourself every time you need one is part of a SB
	//concept called INVERSION OF CONTROL
	
	//implementing an established aspect (WebMvcConfigurer) would allow us to overwrite standard behaviors

}
