package com.api.tweet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.api.tweet.dao.Tweets;
import com.api.tweet.dao.TweetsLike;
import com.api.tweet.dao.TweetsReply;
import com.api.tweet.entity.Tweet;
import com.api.tweet.entity.TweetLikes;
import com.api.tweet.entity.TweetReply;
import com.api.tweet.exception.TweetAddFailedException;
import com.api.tweet.exception.TweetNotFoundException;
import com.api.tweet.model.ReplyRequest;
import com.api.tweet.model.Users;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TweetsServiceImpl implements TweetsService {
	
	@Autowired
	Tweets tweetsDao;
	@Autowired
	TweetsReply tweetsReplyDao;
	@Autowired
	TweetsLike tweetsLikeDao;
	
	@Autowired
	KafkaTemplate<String, Tweet> kafkaTemplate;


	@Override
	public List<Tweet> getAllTweets(String userId) {
		log.info("Entering getAllTweets service method");
		log.info("Exiting getAllTweets service method");
		return convert(tweetsDao.findAll(),userId);
		
	}
	

	@Override
	public List<Tweet> getTweetByUserId(String userId) {
		log.info("Entering getTweetByUserId service method with userid = "+userId);
		log.info("Exiting getTweetByUserId service method with userid = "+userId);
		return convert(tweetsDao.findByUserName(userId),userId);
	}

	@Override
	public List<Tweet> getTweetByUserNameRegex(String nameRegex,String userId) {
		log.info("Entering getTweetByUserNameRegex service method with name Regex = "+nameRegex);
		log.info("Exiting getTweetByUserNameRegex service method with name Regex = "+nameRegex);
		return convert(tweetsDao.findByUserNameRegex(nameRegex),userId);
	}

	@Override
	public List<Tweet> getTweetByUserName(String name,String userId) {
		log.info("Entering getTweetByUserName service method with user name ="+name);
		log.info("Exiting getTweetByUserName service method with user name ="+name);
		return convert(tweetsDao.findByCreatedByName(name),userId);
	}

	@Override
	public Tweet addTweet(Tweet tweet,Users users) {
		log.info("Entering addTweet service method");
		tweet.setCreatedByName(users.getFirstName()+" "+users.getLastName());
		tweet.setCreatedById(users.getUserId());
		tweet.setUpdateDateTime(LocalDateTime.now());
		tweet.setTweetLikesCount(0);
		log.info("tweet :{}",tweet.toString());
		log.info("user :{}",users.toString());
		/*ListenableFuture<SendResult<String, Tweet>> future = kafkaTemplate.send("tweet", tweet);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Tweet>>() {
		    @Override
		    public void onSuccess(SendResult<String, Tweet> result) {
		    	log.info("Successfully published to tweet topic");

		    }
		    @Override
		    public void onFailure(Throwable ex) {
		        log.info(ex.getMessage());
		        throw new TweetAddFailedException();
		    }
		});
		log.info("Exiting addTweet service method");*/
		return tweetsDao.save(tweet);
		
	}

	@Override
	public Tweet updateTweet(String userId, Tweet tweet) {
		log.info("Entering updateTweet service method");
		Tweet dbtweet = tweetsDao.findById(tweet.getTweetId()).orElseThrow(TweetNotFoundException::new);
		
		log.info("Tweet data from user :{}"+tweet.toString());
		log.info("Tweet data from db :{}"+dbtweet.toString());
		if(dbtweet.getCreatedById().equals(userId)) {
			dbtweet.setUpdateDateTime(LocalDateTime.now());
			dbtweet.setMessage(tweet.getMessage());
			if(tweet.getTag()!=null)dbtweet.setTag(tweet.getTag());
			tweetsDao.save(dbtweet);
		}
		log.info("Tweet data after update ="+dbtweet.toString());
		return tweet;
	}

	@Override
	
	public Tweet deleteTweet(UUID tweetId) {
		log.info("Entering deleteTweet service method");
		log.info("Exiting deleteTweet service method");
		try{
			Tweet tweet = tweetsDao.findById(tweetId).orElse(null);
			tweetsDao.deleteById(tweetId);
			return mapTotweet(tweet, null);
		}catch (Exception e) {
			log.info("Unexpected Error occured  "+e.getMessage());
			return null;
		}
		
	}
	

	@Override
	public Tweet likeTweet(UUID tweetId,String userId) {
		log.info("Entering likeTweet service method");
		Tweet tweet = tweetsDao.findById(tweetId).orElseThrow(TweetNotFoundException::new);
		log.info("tweet :{}",tweet.toString());
		List<TweetLikes> list=tweet.getTweetLikes();
		List<TweetLikes> userliked =  list.stream().filter(a->a.getUserId().equals(userId)).collect(Collectors.toList());
		if(userliked.isEmpty()) {
			TweetLikes likes=new TweetLikes();
			likes.setLikedTime(LocalDateTime.now());
			likes.setUserId(userId);
			likes.setTweet(tweet);
			list.add(likes);
			tweet.setTweetLikesCount(tweet.getTweetLikesCount()+1);
		}else {
			list.remove(userliked.get(0));
			tweet.setTweetLikesCount(tweet.getTweetLikesCount()-1);
		}
		log.info("Exiting likeTweet service method");
		return mapTotweet(tweetsDao.save(tweet),userId);
		
	}
	


	@Override
	public Tweet replyTweet(ReplyRequest replyRequest,String userId, UUID tweetId) {
		log.info("Entering reply tweet service method");
		Tweet tweet = tweetsDao.findById(tweetId).orElseThrow(TweetNotFoundException::new);
		log.info("Tweet fromdb :{}"+ tweet.toString());
		List<TweetReply> replyList = tweet.getTweetReply();
		TweetReply reply=new TweetReply();
		reply.setCreationTime(LocalDateTime.now());
		reply.setReplyMsg(replyRequest.getReplyMsg());
		reply.setUserId(userId);
		log.info("Rely{} "+reply);
		replyList.add(reply);
		return mapTotweet(tweetsDao.save(tweet),userId);
		
	}
	
	private Tweet mapTotweet(Tweet tweet,String id){
		log.info("Entering mapTotweet service method");
		log.info("tweet :{}"+tweet.toString());
		tweet.setHasLiked(false);
		for(TweetReply reply : tweet.getTweetReply())
				reply.setTweet(null);
		for(TweetLikes likes : tweet.getTweetLikes()) {
			likes.setTweet(null);
			if(likes.getUserId().equals(id)) {
				tweet.setHasLiked(true);
			}
		}
		log.info("Exiting mapTotweet service method");
		return tweet;
	}
	private List<Tweet> convert(List<Tweet> initialList,String id){
		log.info("Entering convert service method");
		List<Tweet> finalList=new ArrayList<>();
		for(Tweet tweet:initialList) {
			log.info("tweet :{}"+tweet.toString());
			finalList.add(mapTotweet(tweet,id));
		}
		log.info("Entering convert service method");
		return finalList;
	}
	

}
