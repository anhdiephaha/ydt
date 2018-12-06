package com.ydt.payload;


import com.ydt.entity.Roles;
import com.ydt.entity.Users;

import java.util.List;

public class LoginResponse {
	private String accessToken;
	private Users username;

	public LoginResponse() {

	}

	public LoginResponse(String accessToken, Users username) {
		this.accessToken = accessToken;
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Users getUsername() {
		return username;
	}

	public void setUsername(Users username) {
		this.username = username;
	}

}
