package com.onerivet.service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {
	
	public String genrateToken(String name) {
		
		Map<String,Object> claims=new HashMap<>();
		return createToken(name,claims);
		
	}
	
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


	 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts
	                .parserBuilder()
	                .setSigningKey(getSignKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
	    
	    public java.util.Date extractionExpiration(String token){
			return extractClaim(token,Claims::getExpiration);
	    	
	    }

	    private Boolean isTokenExpired(String token) {
			return extractionExpiration(token).before(new Date());
	    	
	    }

	    
		public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()));
	    }
	

	private String createToken(String name, Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(name)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
		// TODO Auto-generated method stub
		
	}

	private Key getSignKey() {
		byte[] keysbytes=Decoders.BASE64.decode("51655468576D597133743677397A24432646294A404E635266556A586E327235");
		return Keys.hmacShaKeyFor(keysbytes);
	}

}
