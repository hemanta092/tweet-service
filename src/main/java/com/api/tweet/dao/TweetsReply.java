package com.api.tweet.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.tweet.entity.TweetReply;

@Repository
public interface TweetsReply extends JpaRepository<TweetReply, UUID>{

}
