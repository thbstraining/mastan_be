package com.example.library.exception;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException() {
		super();
		
	}

	public UserNotFoundException(String message) {
		super(message);
		
	}
	

}
