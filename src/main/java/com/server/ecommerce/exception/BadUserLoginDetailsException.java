package com.server.ecommerce.exception;

public class BadUserLoginDetailsException extends RuntimeException{

	public BadUserLoginDetailsException(){
		super("Invaild password");
		
	}
	public BadUserLoginDetailsException(String Message) {
		super(Message);
		
	}
	

	

}
