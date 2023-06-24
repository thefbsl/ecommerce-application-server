package com.server.ecommerce.controller;

import com.server.ecommerce.dto.UserDto;
import com.server.ecommerce.exception.BadUserLoginDetailsException;
import com.server.ecommerce.payload.JwtRequest;
import com.server.ecommerce.payload.JwtResponse;
import com.server.ecommerce.security.JwtHelper;
import com.server.ecommerce.service.implementation.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private AuthenticationManager manger;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private JwtHelper jwthelper;
	@Autowired
	private ModelMapper mapper;
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)throws Exception{
		authenticateUser(request.getUsername(),request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

		String token = jwthelper.generateToken(userDetails);
		
		JwtResponse response = new JwtResponse();
		response.setToken(token);
		response.setUser(mapper.map(userDetails, UserDto.class));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

	//Authenticate
	private void authenticateUser(String username, String password) {
		try {
		manger.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(BadCredentialsException e){
			throw new BadUserLoginDetailsException("Invaild Username or Password");
			
		}catch(DisabledException e) {
			throw new BadUserLoginDetailsException("User is disable");
		}
	}
}
