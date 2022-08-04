package com.api.tweet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tweet.client.AuthClient;
import com.api.tweet.entity.Tweet;
import com.api.tweet.model.ReplyRequest;
import com.api.tweet.model.Users;
import com.api.tweet.model.ValidationRespose;
import com.api.tweet.service.TweetsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tweet")
@Slf4j
@CrossOrigin
public class TweetController {
	
	@Autowired
	AuthClient authClient;
	
	@Autowired
	TweetsService tweetsService;
	
	@GetMapping( value ="/getAllTweets")
	public ResponseEntity<List<Tweet>> getAllTweets(@RequestHeader(name ="Authorization") String token) {
		log.info("Entering getAllTweets controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("Exiting getAllTweets controller method.");
		if(respose.isValid()) {
			Users user = authClient.getUserByUserId(token);
			return new ResponseEntity<>(tweetsService.getAllTweets(user.getUserId()),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
		
	}
	
	@GetMapping(value="/searchByUserName/{userName}")
	public ResponseEntity<List<Users>> searchByUserName(@RequestHeader(name ="Authorization") String token,
			@PathVariable(name ="userName") String userName){
		log.info("Entering searchByUserName controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("Exiting searchByUserName controller method.");
		if(respose.isValid()) {
			return new ResponseEntity<>(authClient.searchByUserName(token, userName),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
		
	}
	
	@GetMapping( value ="/getTweetsByUserId/{userId}")
	public ResponseEntity<List<Tweet>> getTweetByUserId(@RequestHeader(name ="Authorization") String token,
			@PathVariable(name ="userId") String userId) {
		log.info("Entering getTweetByUserId controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("Exiting getTweetByUserId controller method.");
		if(respose.isValid()) {
			return new ResponseEntity<>(tweetsService.getTweetByUserId(userId),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
		
	}
	@GetMapping( value ="/getTweetsByUserNameRegex/{userName}")
	public ResponseEntity<List<Tweet>> getTweetsByUserNameRegex(@RequestHeader(name ="Authorization") String token,
			@PathVariable(name ="userName") String userName) {
		log.info("Entering getTweetsByUserNameRegex controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("Exiting getTweetsByUserNameRegex controller method.");
		if(respose.isValid()) {
			Users user = authClient.getUserByUserId(token);
			return new ResponseEntity<>(tweetsService.getTweetByUserNameRegex(userName,user.getUserId()),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
		
	}
	
	@GetMapping( value ="/getTweetsByUserName/{userName}")
	public ResponseEntity<List<Tweet>> getTweetByUserName(@RequestHeader(name ="Authorization") String token,
			@PathVariable(name ="userName") String userName) {
		log.info("Entering getTweetByUserName controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("Exiting getTweetByUserName controller method.");
		if(respose.isValid()) {
			Users user = authClient.getUserByUserId(token);
			return new ResponseEntity<>(tweetsService.getTweetByUserName(userName,user.getUserId()),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
		
	}
	
	@GetMapping( value ="/getAllUsers")
	public ResponseEntity<List<Users>> getAllUsers(@RequestHeader(name ="Authorization") String token) {
		log.info("Entering getAllUsers controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("Exiting getAllUsers controller method.");
		if(respose.isValid()) {
			log.info("Returning list of users");
			return new ResponseEntity<>(authClient.getAllUsers(token),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
		
	}
	@PostMapping(value="/addTweet")
	public ResponseEntity<Tweet> addTweet(@RequestHeader(name ="Authorization") String token,
			@Valid @RequestBody Tweet tweet){
		log.info("Entering addTweet controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("tweet = "+tweet.toString());
		Users user = authClient.getUserByUserId(token);
		if(null != user) {
			log.info("User= "+ user.toString());
			log.info("Exiting addTweet controller method.");
			if(respose.isValid()) {
				log.info("Returning after saving tweet");
				return new ResponseEntity<>(tweetsService.addTweet(tweet, user),HttpStatus.OK);
			}
			log.info("Token Expired: logging off user" );
			if(user.isActive())authClient.logout(token);
		}
		log.info("User does not exist for the token");
		return new ResponseEntity<>(new Tweet(),HttpStatus.FORBIDDEN);
		
	}
	@GetMapping(value="/deleteTweet/{tweetId}")
	public ResponseEntity<Tweet> deleteTweet(@RequestHeader(name ="Authorization") String token,
			@PathVariable(name ="tweetId") UUID tweetId){
		log.info("Entering deleteTweet controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("Exiting deleteTweet controller method.");
		if(respose.isValid()) {
			log.info("deleting tweets");
			return new ResponseEntity<>(tweetsService.deleteTweet(tweetId),HttpStatus.OK);
		}
		log.info("Token Expired: logging off user" );
		return new ResponseEntity<>(new Tweet(),HttpStatus.FORBIDDEN);
		
	}
	@PostMapping(value="/updateTweet")
	public ResponseEntity<Tweet> updateTweet(@RequestHeader(name ="Authorization") String token,
			@Valid @RequestBody Tweet tweet){
		log.info("Entering updateTweet controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("tweet = "+tweet.toString());
		Users user = authClient.getUserByUserId(token);
		if(null != user) {
			log.info("User= "+ user.toString());
			log.info("Exiting updateTweet controller method.");
			if(respose.isValid()) {
				log.info("updating tweets");
				return new ResponseEntity<>(tweetsService.updateTweet(user.getUserId(),tweet),HttpStatus.OK);
			}else {
				log.info("Token Expired: logging off user" );
				if(user.isActive())authClient.logout(token);
			}
			
		}
		log.info("User does not exist for the token");
		return new ResponseEntity<>(new Tweet(),HttpStatus.FORBIDDEN);
		
	}
	
	@GetMapping(value = "/likeTweet/{tweetId}")
	public ResponseEntity<Tweet> likeTweet(@RequestHeader(name ="Authorization") String token,
			@PathVariable (name ="tweetId") UUID tweetId){
		log.info("Entering likeTweet controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		Users user = authClient.getUserByUserId(token);
		if(null != user) {
			log.info("User= "+ user.toString());
			log.info("Exiting likeTweet controller method.");
			if(respose.isValid()) {
				log.info("Returning likes");
				return new ResponseEntity<>(tweetsService.likeTweet(tweetId, user.getUserId()),HttpStatus.OK);
			}
			log.info("Token Expired: logging off user" );
			if(user.isActive())authClient.logout(token);
		}
		log.info("User does not exist for the token");
		return new ResponseEntity<>(new Tweet(),HttpStatus.FORBIDDEN);
	}
	@PostMapping("/replyTweet/{tweetId}")
	public ResponseEntity<Tweet> replyTweet(@RequestHeader(name ="Authorization") String token,
			@RequestBody ReplyRequest replyRequest,@PathVariable (name ="tweetId") UUID tweetId){
		log.info("Entering replyTweet controller method.");
		ValidationRespose respose = authClient.getValidity(token);
		log.info("token ="+token);
		log.info("reply msg = "+replyRequest.getReplyMsg());
		Users user = authClient.getUserByUserId(token);
		if(null != user) {
			log.info("User= "+ user.toString());
			log.info("Exiting replyTweet controller method.");
			if(respose.isValid()) {
				log.info("Returning  tweetReply");
				return new ResponseEntity<>(tweetsService.replyTweet(replyRequest, user.getUserId(), tweetId),HttpStatus.OK);
			}
			log.info("Token Expired: logging off user" );
			if(user.isActive())authClient.logout(token);
		}
		log.info("User does not exist for the token");
		return new ResponseEntity<>(new Tweet(),HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/logout")
	public ValidationRespose logout(@RequestHeader("Authorization") String token) {
		log.info("Inside Logout");
		return authClient.logout(token);
	}
}
