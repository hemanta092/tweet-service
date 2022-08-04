package com.api.tweet.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetReply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID tweetreplyId;
	private String userId;
	private String replyMsg;
	private LocalDateTime creationTime;
	@ManyToOne
	@JoinColumn(name = "tweetId")
	private Tweet tweet;
	

}
