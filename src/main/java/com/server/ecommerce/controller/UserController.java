package com.server.ecommerce.controller;

import com.server.ecommerce.dto.UserDto;
import com.server.ecommerce.payload.ApiResponse;
import com.server.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date=new Date();
		formatter.format(date);
		userDto.setDate(date);
		userDto.setActive(true);
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return new ResponseEntity<>(userService.create(userDto),HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN')")   
	@PutMapping("/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable int userId,@RequestBody UserDto ud) {
    	return new ResponseEntity<>(userService.update(ud, userId),HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ADMIN')")   
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> delete(@PathVariable int userId) {
		userService.delete(userId);
	    return new ResponseEntity<>(new ApiResponse("User is Deleted",true),HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/")
	public ResponseEntity<List<UserDto>>getAll() {
		return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('STAFF')")   
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
		return new ResponseEntity<>(userService.getByUserId(userId),HttpStatus.OK);
		
	}
	@PreAuthorize("hasRole('ADMIN','STAFF')")   
	@GetMapping("email/{email}")
	public ResponseEntity<UserDto>getUserByEmail(@PathVariable String email){
		return new ResponseEntity<>(userService.getByEmailId(email) ,HttpStatus.OK);
	}
}
