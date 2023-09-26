package com.example.originalSecurity.token;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken  {

	 public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
//	 @Value("${jwt.secret}")
	    private String secretKey="secretkey123";
	

	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }


	    public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }
	    
	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }
	    

	    private Claims getAllClaimsFromToken(String token) {         //111111111111111
	        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	    }
	    

	    private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }

	    
	    public String generateToken(UserDetails userDetails) {   //111111
	        Map<String, Object> claims = new HashMap<>();
	        return doGenerateToken(claims, userDetails.getUsername());
	    }
	    
	   
	    private String doGenerateToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
	    }
	    

	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	    
	    
}









//package com.example.originalSecurity.token;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Component
//public class JwtToken {
//	
//	public static final long JWT_TOKEN_VALIDITY=5*60*60;
//
//	private String secretKey="secretkey123";
//	
//	public String doGenerateToken(Map<String,Object> /*hashMap*/claims,String username/*subject*/ ) {
//		return Jwts.builder()
//		              .setClaims(/*hashMap*/claims).setSubject(username/*subject*/)
//		              .setIssuedAt(new Date(System.currentTimeMillis()))
//		              .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY * 1000))
//		              .signWith(SignatureAlgorithm.HS512, secretKey)
//		              .compact();
//		
//	}
//	
//	public String generateToken(UserDetails userDetails) {
//		Map<String,Object> /*hashMap*/claims=new HashMap<>();
//		return doGenerateToken(/*hashMap*/claims, userDetails.getUsername());
//	}
//	
//	
//	
//	
//	
//	private Claims /*getClaimsFromToken*/getAllClaimsFromToken(String token){
//		return Jwts.parser()
//		             .setSigningKey(secretKey)
//                     .parseClaimsJws(token)	
//                     .getBody();
//                     
//    }
//	
//	private<T> T   getClaimFromToken(String token,Function<Claims, T> resolver) {
//		Claims claims = getAllClaimsFromToken(token);
//		return resolver.apply(claims);
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	public String getUsernameFromToken(String token) {
//		return getClaimFromToken(token, Claims::getSubject);
//	}
//
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}
//
//	private boolean isTokenExpired(String token) {
//		final Date expirationDate = getExpirationDateFromToken(token);
//		return expirationDate.before(new Date());
//	}
//	
//	
//	
//	
//	
//	public Boolean validateToken(String token,UserDetails userDetails) {
//		String username = getUsernameFromToken(token);
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
//	}
//	
//}
