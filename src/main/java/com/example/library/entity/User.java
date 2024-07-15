package com.example.library.entity;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@NotNull(message="userName must be specified")
	@NotEmpty(message="userName must be specified")
	private String userName;
	
	@NotNull(message="password must be specified")
	@NotEmpty(message="password must be specified")
	private String password;
	
	@NotNull(message="roles must be specified")
	@NotEmpty(message="roles must be specified")
	private List<String>roles;

	public User() {
		super();
		
	}

	public User(long userId,
			@NotNull(message = "userName must be specified") @NotEmpty(message = "userName must be specified") String userName,
			@NotNull(message = "password must be specified") @NotEmpty(message = "password must be specified") String password,
			@NotNull(message = "roles must be specified") @NotEmpty(message = "roles must be specified") List<String> roles) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.roles = roles;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
	

}
