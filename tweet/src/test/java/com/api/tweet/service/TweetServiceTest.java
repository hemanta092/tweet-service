package com.api.tweet.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import com.api.tweet.dao.Tweets;
import com.api.tweet.entity.Tweet;
import com.api.tweet.entity.TweetLikes;
import com.api.tweet.model.ReplyRequest;
import com.api.tweet.model.Users;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {
	
	@Mock
	Tweets repoTweets;
	
	@InjectMocks
	TweetsServiceImpl service;
	@Mock
	KafkaTemplate<String, Tweet> kafkaTemplate;
	
	@Test
	public void testGetAllTweets() throws Exception {
		List<Tweet> list = new ArrayList<>();
		list.add(getTweet());
		when(repoTweets.findAll()).thenReturn(list);
		List<Tweet> returnedList = service.getAllTweets("userIs");
		assertNotNull(returnedList);
		assertEquals(1, returnedList.size());
		
		
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
		List<Tweet> list = new ArrayList<>();
		list.add(getTweet());
		when(repoTweets.findByUserName(anyString())).thenReturn(list);
		List<Tweet> returnedList = service.getTweetByUserId("userId");
		assertNotNull(returnedList);
		assertEquals(1, returnedList.size());
		
	}
	
	@Test
	public void getTweetsByUserNameRegex() throws Exception {
		List<Tweet> list = new ArrayList<>();
		list.add(getTweet());
		when(repoTweets.findByUserNameRegex(anyString())).thenReturn(list);
		List<Tweet> returnedList = service.getTweetByUserNameRegex("nameRegex","userId");
		assertNotNull(returnedList);
		assertEquals(1, returnedList.size());
	}
	@Test
	public void getTweetByUserName() throws Exception {
		List<Tweet> list = new ArrayList<>();
		list.add(getTweet());
		when(repoTweets.findByCreatedByName(anyString())).thenReturn(list);
		List<Tweet> returnedList = service.getTweetByUserName("userName","userId");
		assertNotNull(returnedList);
		assertEquals(1, returnedList.size());
	}
	
	
	@Test
	public void addTweet() throws Exception {
		ListenableFuture<SendResult<String, Tweet>> future=new SettableListenableFuture<SendResult<String,Tweet>>();
		when(kafkaTemplate.send(anyString(),any())).thenReturn(future);
		Tweet tweet = service.addTweet(getTweet(),getUser());
		assertEquals("First Last", tweet.getCreatedByName());
		
	}
	
	@Test
	public void deleteTweet() throws Exception {
		
		service.deleteTweet(UUID.randomUUID());
		verify(repoTweets).deleteById(any());
		
	}
	
	@Test
	public void updateTweet() throws Exception {
		when(repoTweets.findById(any())).thenReturn(Optional.of(getTweet()));
		Tweet tweet = service.updateTweet("userId",getTweet());
		assertEquals("userId", tweet.getCreatedById());
		
	}
	
	
	@Test
	public void likeTweet() throws Exception {
		when(repoTweets.findById(any())).thenReturn(Optional.of(getTweet()));
		Tweet tweet=getTweet();
		tweet.setTweetLikesCount(1);
		TweetLikes likes = new TweetLikes();
		likes.setTweetLikeId(UUID.randomUUID());
		likes.setUserId("userId");
		
		tweet.setTweetLikes(new ArrayList<>());
		List<TweetLikes> likeList = tweet.getTweetLikes();
		likeList.add(likes);
		tweet.setTweetLikes(likeList);
		when(repoTweets.save(any())).thenReturn(tweet);
		Tweet dbtweet = service.likeTweet(UUID.randomUUID(),"userId");
		assertEquals(1, dbtweet.getTweetLikes().size());
		assertEquals(1, dbtweet.getTweetLikesCount());
		
	}
	@Test
	public void replyTweet() throws Exception {
		ListenableFuture<SendResult<String, Tweet>> future=new SettableListenableFuture<SendResult<String,Tweet>>();
		when(kafkaTemplate.send(anyString(),any())).thenReturn(future);
		Tweet tweet = new Tweet();
		tweet.setTweetId(UUID.randomUUID());
		tweet.setCreatedById("userId");
		tweet.setCreatedByName("Test Name");
		tweet.setMessage("Hello");
		tweet.setTweetLikesCount(0);
		tweet.setUpdateDateTime(LocalDateTime.now());
		tweet.setTweetLikes(new ArrayList<>());
		tweet.setTweetReply(new ArrayList<>());
		when(repoTweets.findById(any())).thenReturn(Optional.of(tweet));
		when(repoTweets.save(any())).thenReturn(tweet);
		ReplyRequest replyRequest=new ReplyRequest();
		replyRequest.setReplyMsg("Test");
		Tweet tweetRes = service.replyTweet(replyRequest,"userId",UUID.randomUUID());
		//verify(kafkaTemplate).send(anyString(),any());
		assertNotNull(tweetRes);
		
	}
	

}
