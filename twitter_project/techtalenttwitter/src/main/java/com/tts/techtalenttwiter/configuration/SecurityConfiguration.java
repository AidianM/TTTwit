package com.tts.techtalenttwiter.configuration;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	//So this is apparently very recently deprecated, but we're gonna push on-
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{ //SB will handle it if there's an error
		//This has a default implementation; we're overwriting it.
		//We're going to customize how authentication occurs
		auth.jdbcAuthentication() //we want to authenticate using a Java Database Connection Backend; 
								//(ie our authentication info is in a database)
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource) //tells Spring Sec what database to query (here, the standard one from H2)
			.passwordEncoder(bCryptPasswordEncoder);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/console/**").permitAll() //web console visible
			.antMatchers("/login").permitAll() //login page visible
			.antMatchers("/signup").permitAll() //signup page visible
			.antMatchers("/custom.js").permitAll() //access JS file
			.antMatchers("/custom.css").permitAll() //access our CSS file
			//all allowable, logged in or not
			.antMatchers().hasAuthority("USER").anyRequest().authenticated() //any user has the authority when logged in
		.and()
			.formLogin() //in order to log in, we're going to have a form on a web page
			.loginPage("/login").failureUrl("/login?error=true") //provides our login, and a page for failure
			.defaultSuccessUrl("/tweets")
			.usernameParameter("username")
			.passwordParameter("password")
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
		.and()
			.exceptionHandling()
		.and()
			.csrf().disable()
			.headers().frameOptions().disable();
	}
	//specifies pages access, not files access, requiring the below WebSecurity config:
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
			.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	} //allowing access to any files in respective locations
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
