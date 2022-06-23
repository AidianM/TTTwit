Spring Boot Twitter Project
3 day span
25-27 May 2022
-----------------------------------------------------------------------------------------------------
=====================================================================================================
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                    ++BEGIN TRANSMISSION++
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Some projects demand a back-end. React/Angular run no server code. They can be hosted anywhere, but if you have, for example, a Spring Boot project, you have to send not just the static HTML/CSS to the client to be served, you also need to send Java code that will run on the server. This demands a host/web service that will function with Spring Boot.

-----------------------------------------------------------------------------------------------------

Quite probably the largest Spring Boot project we'll do this course.

-----------------------------------------------------------------------------------------------------

--Install: Project Lombok to Eclipse IDE
    (resource: https://projectlombok.org/download)

--Verify: Help/About Eclipse IDE/
    "Lombok v1.18.24 "Envious Ferret" is installed."
-----------------------------------------------------------------------------------------------------

New Spring Starter Project
    Project Dependencies:
        Spring Boot DevTools
        Lombok
        Validation
        Spring Data JPA
        H2 Database
        Spring Security
        Thymeleaf
        Spring Web

***POM.XML ERROR**
    --install new software//tm4e
    --resolved

"Always some gremlins at the beginning of this project. Lombok does like to...not...work..."

-----------------------------------------------------------------------------------------------------

1. Inspect pom.xml
    Add dependency:
        <dependency>
			<groupId>org.ocpsoft.prettytime</groupId>
			<artifactId>prettytime</artifactId>
			<version>4.0.1.Final</version>
		</dependency>

    Pulls from Maven respository (like npm). groupId specifies unique identifier for organization, artifactId for the dependency, version (else default to latest release)

    "PrettyTime is an OpenSource time formatting library. Completely customizable, it creates human readable, relative timestamps like those seen on Digg, Twitter, and Facebook."

2. Create 5 child packages in src/main/java
    --DO NOT put your code is src/test/java. Someone always does.

    com.tts.techtalenttwitter - new folders:
        1. configuration
        2. controller
        3. model
        4. repository
        5. service

3. src/main/resources/application.properties
    (see: Day 1 slide #12)
    Adding a lot of config:
        #H2 database setup:

        spring.h2.console.enabled = true
        spring.h2.console.path = /console

        spring.datasource.url = jdbc:h2:mem:twitterdb
        spring.datasource.username = sa
        spring.datasource.password = 
        #don't deploy with no password!

        spring.datasource.driver-class-name = org.h2.Driver

        #Show SQL queries in console for debugging:
        spring.jpa.show-sql = true

        #!!Critical!!
        #This will cause provided .sql files in /resources to automatically execute on startup:
        spring.sql.init.mode = always

        #Update database description layer when app start:
        spring.jpa.hibernate.ddl-auto = update
        #without, won't update database with code changes automatically (to avoid harming production release)

        #Set up queries for loading users and roles:
        spring.queries.users-query = select username, password, active from user where username=?
        spring.queries.roles-query = select u.username, r.role from user u inner join user_role ur on(u.user_id=ur.user_id) inner join role r on(ur.role_id=r.role_id) where u.username=?

4. /resources:
    new file: data.sql
    new folder: templates/fragments
    new files: /static/ custom.css && custom.js

-----------------------------------------------------------------------------------------------------
**note: typo, techtalent"twiter" --refactor later**

-model/User.java
-model/Role.java
-repository/UserRepository && RoleRepository
-service/UserService

Type Check:
-----------
models: Classes
repository: Interfaces
service: Class

-data.sql (BACK. TICKS.)
    (create a single role for database with ID:1 Value:User)
    MERGE INTO is H2 specific; like join but will set if not *already* set

-configuration/WebMvcConfiguration 
-configuration/ThymeleafConfiguration  
-configuration/SecurityConfiguration

=====================================================================================================
=====================================================================================================
                                        ++BEGIN DAY 2++
                                          26 MAY 2022
==========================================================================================================================================================================================================

(begins: Day1 Slides #38)
Ordo:
-Inspect pom.xml
-Create 5 child packages in src/main/java
-src/main/resources/application.properties
-/resources:
    new file: data.sql
    new folder: templates/fragments
    new files: /static/ custom.css && custom.js
-model/User.java
-model/Role.java
-repository/UserRepository && RoleRepository
-service/UserService
-data.sql (BACK. TICKS.)
    (create a single role for database with ID:1 Value:User)
-configuration/WebMvcConfiguration 
-configuration/ThymeleafConfiguration  
-configuration/SecurityConfiguration

-/controller/AuthorizationController
-/resources/login.html
-resources/registration.html

<form th:action="@{/login}" method="post">  
//Adding th: to the action lets Thymeleaf have a crack at it


-/model/tweet.java

=====================================================================================================
=====================================================================================================
                                        ++BEGIN DAY 3++
                                          27 MAY 2022
==========================================================================================================================================================================================================
(begin: Day 1 slide#66)

Ordo:
-Inspect pom.xml
-Create 5 child packages in src/main/java
-src/main/resources/application.properties
-/resources:
    new file: data.sql
    new folder: templates/fragments
    new files: /static/ custom.css && custom.js
-model/User.java
-model/Role.java
-repository/UserRepository && RoleRepository
-service/UserService
-data.sql (BACK. TICKS.)
    (create a single role for database with ID:1 Value:User)
-configuration/WebMvcConfiguration 
-configuration/ThymeleafConfiguration  
-configuration/SecurityConfiguration

-/controller/AuthorizationController
-/resources/login.html
-resources/registration.html
-/model/tweet.java
-/controller/TweetController
-templates/feed.html && newTweet.html

-----------------------------------------------------------------------------------------------------

Re: @Validators - javadoc contains a plentitude for many circumstances (email, user names, no scripted elements, credit card formats, et al)

=====================================================================================================
-----------------------------------------------------------------------------------------------------
Begin Slide Deck 2:
-----------------------------------------------------------------------------------------------------
=====================================================================================================

-/fragments/header.html

ISSUES:
fixed-/users/user borked

=====================================================================================================
-----------------------------------------------------------------------------------------------------
Begin DAY 3 31 MAY 2022:
-----------------------------------------------------------------------------------------------------
=====================================================================================================

TODO:
++Unfollow warning not showing; suspect class issue in html

Dig Deeper:
@JoinColumn/re: SQL commands and Java

ORDO:
Users.java
Tag.java
TagRepository.java
TweetService.java
    Regex: Java.lang.Pattern

TweetRepository.java
    new query


=====================================================================================================
-----------------------------------------------------------------------------------------------------
Begin Slide Deck #3:
-----------------------------------------------------------------------------------------------------
=====================================================================================================

Link Shortening:
similar to tag formatting

ORDO:
TweetService.java
TweetDisplay

Timestamp:
    Tweet to TweetService to thymeleaf display
    TL can't format dates better, so we can't push raw data and have to prettify it ourselves via
    tweetDisplay


Update to feed == only following:










































































+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                        ++APOCRYPHA++
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



-----------------------------------------------------------------------------------------------------
"You'll have to wait for office hours, but I won't be here for office hours today, so you'll have to wait until...Tuesday."
-----------------------------------------------------------------------------------------------------

"I guess we'll take care of Adrienne's problems and everyone else can have a longer break."
-----------------------------------------------------------------------------------------------------

"I don't want to help you. I mean I don't *not* want to help you."
-----------------------------------------------------------------------------------------------------

"We aren't covering regex but you...should."
-----------------------------------------------------------------------------------------------------

"I'm having an issue."
*instructor halts mid-process*
*A muted*
*finally resolves communique*
"Oh, no, it's fine. Nevermind."
Another stellar interaction👍.

--another issue--
*not finding Eclipse IDE, waits to end of install lecture portion to begin*
*sharing*
"Do you have a desktop icon for eclipse?..no, don't go into desktop, do you have one? Just minimize the window...no, you stopped sharing."

"Go to line 52. That's not line 52. There are numbers on the side..."
"WHAT DID YOU DO TO THIS FILE?!?! Somehow you typed an illegal character into the file?? I don't know how you did this. Ok, so what you're going to do is..."

"...no, to the left of it. That's the right. To the left. Select everything to the left. Left."

"No, the problems pane. The pane. Do you know what a pane is? It's a window. So at the bottom of your screen there are windows with panes. At the top of that pane is a title. You need to click on problems right above that. The pane tab that says problems."

//plenty of issues with people setting up, various gremlins, but A is at LEAST 4:1 A:everyone

"I need you to expand the description field. Do you...know how to do that? You see where the fields are separated by a vertical line? I need you to hover over that, then left click and drag it to the right. No. To the right."

----

"I have problems...I have...red...."
"You have an error?"
"I have...red..."
"In what file?"
"User service lines 31...36...42...a lot of them..."