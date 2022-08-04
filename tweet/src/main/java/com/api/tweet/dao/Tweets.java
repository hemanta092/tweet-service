package com.api.tweet.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.tweet.entity.Tweet;

@Repository
public interface Tweets extends JpaRepository<Tweet, UUID> {
	@Query(value = "select t from Tweet t where t.createdById=?1")
	List<Tweet> findByUserName(String userId);
	@Query(value = "select t from Tweet t where t.createdByName like %:nameRegex% ")
	List<Tweet> findByUserNameRegex(String nameRegex);
	
	List<Tweet> findByCreatedByName(String name);
	
	

}
