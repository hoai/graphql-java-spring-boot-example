package com.example.DemoGraphQL.model;

public class AuthData {
	private String authorization;
	private String grant_type;
	private String username;
	private String password;
	
	public AuthData() {
	}

	public AuthData(String authorization, String grant_type, String username, String password) {
		this.authorization = authorization;
		this.grant_type = grant_type;
		this.username = username;
		this.password = password;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
