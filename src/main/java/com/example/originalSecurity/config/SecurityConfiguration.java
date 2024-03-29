package com.example.originalSecurity.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.originalSecurity.config.service.CustomUserDetailsService;
import com.example.originalSecurity.token.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthFilter authFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth
           .userDetailsService(userDetailsService)
           .passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		    .authorizeRequests()
		        .antMatchers("/saveRole","/saveUser").permitAll()
		        .antMatchers("/saveAuth/**").permitAll()
		        .antMatchers("/admin").hasAnyAuthority("ADMIN")
		        .anyRequest().authenticated()
		        .and()
		        .httpBasic()
		        .and()
		        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		        .and()
		        .addFilterBefore(authFilter,UsernamePasswordAuthenticationFilter.class );
		
		
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	
}
