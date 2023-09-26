package com.example.originalSecurity.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.originalSecurity.config.service.CustomUserDetailsService;
import com.example.originalSecurity.entity.Role;
import com.example.originalSecurity.entity.User;
import com.example.originalSecurity.enums.Roles;
import com.example.originalSecurity.repository.RoleRepository;
import com.example.originalSecurity.repository.UserRepository;
import com.example.originalSecurity.token.JwtToken;
import com.example.originalSecurity.token.model.AuthRequest;
import com.example.originalSecurity.token.model.AuthResponse;


@RestController
public class OriginalSecurityController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtToken jwtToken;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService; 
	
	@Autowired
	private RoleRepository roleRepo;
	
	
	
	@PostMapping("/saveRole")
	public String saveRole(@RequestBody Role role) {
		roleRepo.save(role);
		return "Role saved sucessfully";
	}
	
	@PostMapping("/saveUser")
	public User saveUser(@RequestBody User user,@RequestParam Roles[] roles) {
		
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		List emptyRoleList = new ArrayList<>();
		for(Roles role:roles) {
			System.err.println(role);
			Role roleDB = roleRepo.findByRoleName(role);
			System.err.println(roleDB);
			emptyRoleList.add(roleDB);
		
		}
		 user.setRoles(emptyRoleList);
		User userDB = userRepo.save(user);
		 return userDB;
	}
	
	@PostMapping("/saveAuth")
	public AuthResponse generateToken(@RequestBody AuthRequest user) throws Exception {
	    System.err.println("hello0hello0 hello0");
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		System.err.println("hellohello hello");
	     if(auth.isAuthenticated()) {
	    	 System.err.println("hello2hello2 hello2");
	    	UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
	    	String token = jwtToken.generateToken(userDetails);
	    	return new AuthResponse(token);
	     }else {
	    	 throw new Exception();
	     }     
	}
	
	@GetMapping("admin")
	public String admin() {
		return "AdminControl";
	}
	
	@GetMapping("user")
	public String user() {
		return "UserControl";
	}
	
	
}
