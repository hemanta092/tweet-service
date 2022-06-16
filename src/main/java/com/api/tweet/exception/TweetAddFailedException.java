package com.api.tweet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST , reason = "Unexpected error.Addidng Tweet Failed.Please Try Again.")
public class TweetAddFailedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
