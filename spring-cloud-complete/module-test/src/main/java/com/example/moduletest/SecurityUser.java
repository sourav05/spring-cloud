package com.example.moduletest;

public class SecurityUser{

	private static final long serialVersionUID = -1189452675210191896L;
	
	private String username;
	private String password;
	private boolean enabled;
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

}
