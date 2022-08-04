package com.api.tweet.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id" , insertable = false,updatable = false,nullable = false)
	private UUID tweetId;
	@NotBlank(message = "Tweet message cannot be empty")
	@Max(value = 144)
	private String message;
	@Max(value = 50)
	private String tag;
	@NotBlank(message = "User Id cannot be blank")
	private String createdById;
	private String createdByName;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSSSS]")
	private LocalDateTime updateDateTime;
	@Min(value = 0)
	private long tweetLikesCount;
	@Transient
	private boolean hasLiked;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="tweetId")
	private List<TweetReply> tweetReply=new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL )
	@JoinColumn(name="tweetId")
	private List<TweetLikes> tweetLikes=new ArrayList<>();
	
}
