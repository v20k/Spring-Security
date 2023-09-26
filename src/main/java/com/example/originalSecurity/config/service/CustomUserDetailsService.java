package com.example.originalSecurity.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.originalSecurity.config.userDetails.CustomUserDetails;
import com.example.originalSecurity.entity.User;
import com.example.originalSecurity.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = userRepo.findByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("user not found exception");
		}
		
		return new CustomUserDetails(user);
	}

	
}
