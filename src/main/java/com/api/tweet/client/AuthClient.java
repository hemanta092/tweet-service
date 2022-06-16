package com.api.tweet.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.api.tweet.model.Users;
import com.api.tweet.model.ValidationRespose;


@FeignClient(name = "authapp", url = "${auth.url}")
public interface AuthClient {
	
	@GetMapping(value = "/validate")
	public ValidationRespose getValidity(@RequestHeader("Authorization") String token);
	
	@GetMapping(value="/getUserByUserId")
	public Users getUserByUserId(@RequestHeader("Authorization") String token);
	
	@GetMapping(value="/logout")
	public ValidationRespose logout(@RequestHeader("Authorization") String token);
	
	@GetMapping(value="/getAllUsers")
	public List<Users> getAllUsers(@RequestHeader("Authorization") String token);
}
