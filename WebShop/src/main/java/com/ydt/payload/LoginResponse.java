package com.ydt.payload;


import com.ydt.entity.User;

public class LoginResponse {
	private String accessToken;
	private User username;

	public LoginResponse() {

	}

	public LoginResponse(String accessToken, User username) {
		this.accessToken = accessToken;
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public User getUsername() {
		return username;
	}

	public void setUsername(User username) {
		this.username = username;
	}
}
