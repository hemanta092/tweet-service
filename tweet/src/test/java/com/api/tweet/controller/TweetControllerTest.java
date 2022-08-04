package com.api.tweet.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.api.tweet.client.AuthClient;
import com.api.tweet.entity.Tweet;
import com.api.tweet.model.ReplyRequest;
import com.api.tweet.model.Users;
import com.api.tweet.model.ValidationRespose;
import com.api.tweet.service.TweetsService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TweetControllerTest {
	@Mock
	AuthClient authClient;
	@Mock
	TweetsService service;
	@InjectMocks
	TweetController controller;
	
	@Test
	public void testGetAllTweets() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(authClient.getUserByUserId(anyString())).thenReturn(getUser());
		when(service.getAllTweets(anyString())).thenReturn(new ArrayList<>());
		ResponseEntity<List<Tweet>> tweetList = controller.getAllTweets("token");
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	private Tweet getTweet() {
		Tweet tweet = new Tweet();
		tweet.setTweetId(UUID.randomUUID());
		tweet.setCreatedById("userId");
		tweet.setCreatedByName("Test Name");
		tweet.setMessage("Hello");
		tweet.setTweetLikesCount(0);
		tweet.setUpdateDateTime(LocalDateTime.now());
		return tweet;
	}
	private Users getUser() {
		Users user=new Users();
		user.setActive(true);
		user.setGender("Male");
		user.setDateOfBirth(LocalDate.now().minusDays(10).minusMonths(5).minusYears(18));
		user.setFirstName("First");
		user.setLastName("Last");
		user.setMobileNo("9874569874");
		user.setPassword("pass2word");
		user.setUserId("testId");
		return user;
	}
	
	@Test
	public void getTweetByUserId() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(service.getTweetByUserId(anyString())).thenReturn(new ArrayList<Tweet>());
		when(authClient.getUserByUserId(anyString())).thenReturn(getUser());
		ResponseEntity<List<Tweet>> tweetList = controller.getTweetByUserId("token","userId");
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	@Test
	public void getTweetsByUserNameRegex() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(authClient.getUserByUserId(anyString())).thenReturn(getUser());
		when(service.getTweetByUserNameRegex(anyString(),anyString())).thenReturn(new ArrayList<Tweet>());
		ResponseEntity<List<Tweet>> tweetList = controller.getTweetsByUserNameRegex("token","nameReg");
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	@Test
	public void getTweetByUserName() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(authClient.getUserByUserId(anyString())).thenReturn(getUser());
		when(service.getTweetByUserName(anyString(),anyString())).thenReturn(new ArrayList<Tweet>());
		ResponseEntity<List<Tweet>> tweetList = controller.getTweetByUserName("token","name");
		assertEquals(200, tweetList.getStatusCodeValue());
		
		
	}
	@Test
	public void getAllUsers() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(authClient.getAllUsers(anyString())).thenReturn(new ArrayList<>());
		ResponseEntity<List<Users>> tweetList = controller.getAllUsers("token");
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	@Test
	public void addTweet() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(authClient.getUserByUserId(anyString())).thenReturn(getUser());
		when(service.addTweet(any(Tweet.class), any(Users.class))).thenReturn(getTweet());
		ResponseEntity<Tweet> tweetList = controller.addTweet("token",getTweet());
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	@Test
	public void deleteTweet() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(service.deleteTweet(any())).thenReturn(getTweet());
		ResponseEntity<Tweet> tweetList = controller.deleteTweet("token",UUID.randomUUID());
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	@Test
	public void updateTweet() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(authClient.getUserByUserId(anyString())).thenReturn(getUser());
		
		when(service.updateTweet(any(String.class), any(Tweet.class))).thenReturn(getTweet());
		ResponseEntity<Tweet> tweetList = controller.updateTweet("token",getTweet());
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	
	@Test
	public void likeTweet() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(authClient.getUserByUserId(anyString())).thenReturn(getUser());
		
		when(service.likeTweet(any(),any(String.class))).thenReturn(getTweet());
		ResponseEntity<Tweet> tweetList = controller.likeTweet("userId",UUID.randomUUID());
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	@Test
	public void replyTweet() throws Exception {
		when(authClient.getValidity(anyString())).thenReturn(new ValidationRespose(true));
		when(authClient.getUserByUserId(anyString())).thenReturn(getUser());
		when(service.replyTweet(any(ReplyRequest.class), anyString(), any())).thenReturn(new Tweet());
		ResponseEntity<Tweet> tweetList = controller.replyTweet("token",new ReplyRequest(),UUID.randomUUID());
		assertEquals(200, tweetList.getStatusCodeValue());
		
	}
	
	
	

}
