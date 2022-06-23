package com.tts.techtalenttwiter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class ThymeleafConfiguration {
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
	
	//Thymeleaf has certain functions that are added with SpringSecurity (see: pom.xml - "thymeleaf-extras-springsecurity5")
	//SpringSecurityDialect needs this to understand how to integrate with Thymeleaf
	
	//
}
