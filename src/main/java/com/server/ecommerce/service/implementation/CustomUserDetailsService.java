package com.server.ecommerce.service.implementation;

import com.server.ecommerce.exception.ResourceNotFoundException;
import com.server.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Load user From database
		System.out.println("loading user from database");
		return userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	}
}
