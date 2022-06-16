package com.api.tweet.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.api.tweet.dao.Tweets;
import com.api.tweet.entity.Tweet;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TweetListner {
	
	/*@Autowired
	Tweets tweetsDao;
	
	@KafkaListener(topics = "tweet" , groupId = "tweet_group",containerFactory = "kafkaTweetListenerContainerFactory")
	public void consumeTweets(Tweet tweet) {
		log.info("Entering consume tweets method in listner");
		tweetsDao.save(tweet);
		log.info("Exiting consume tweets method in llistner");
		
	}*/

}
