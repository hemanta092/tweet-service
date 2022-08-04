package com.api.tweet.service;

import java.util.List;
import java.util.UUID;

import com.api.tweet.entity.Tweet;
import com.api.tweet.model.ReplyRequest;
import com.api.tweet.model.Users;

public interface TweetsService {
	
	public List<Tweet> getAllTweets(String userId);
	public List<Tweet> getTweetByUserId(String userId);
	public List<Tweet> getTweetByUserNameRegex(String nameRegex,String userId);
	public List<Tweet> getTweetByUserName(String name,String userId);
	public Tweet addTweet(Tweet tweet,Users users);
	public Tweet updateTweet(String userId,Tweet tweet);
	public Tweet deleteTweet(UUID tweetId);
	public Tweet likeTweet(UUID tweetId,String userId);
	public Tweet replyTweet(ReplyRequest reply,String userId, UUID tweetId);
	
}
