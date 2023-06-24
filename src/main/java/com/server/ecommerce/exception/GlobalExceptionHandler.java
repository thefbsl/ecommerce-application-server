package com.server.ecommerce.exception;

import com.server.ecommerce.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	//if id not Found in Service Controller then orElseThrow Method Throw here Here i give Response
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handelResourseNotFoundException(ResourceNotFoundException ex){
		     ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(BadUserLoginDetailsException.class)
	public ResponseEntity<ApiResponse> BadUserLoginDetailsException(BadUserLoginDetailsException ex){
		     ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
		
		Map<String,String> map=new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String message=error.getDefaultMessage();
			String fieldNameString="ErrorField problem";
			map.put(message, fieldNameString);
		});;
		
		return null;
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponse>handelSqlIntegrityException(SQLIntegrityConstraintViolationException sql){
		ApiResponse newapi=new ApiResponse(sql.getMessage(),false);
		
		return new ResponseEntity<ApiResponse>(newapi,HttpStatus.BAD_REQUEST);
	}
	
	
	
	

}
