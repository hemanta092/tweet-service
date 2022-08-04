package com.api.tweet.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {

	private String userId;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate dateOfBirth;
	private LocalDateTime lastSeen;
	private boolean isActive;
	private String mobileNo;
}
