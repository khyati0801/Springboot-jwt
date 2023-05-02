package com.onerivet.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.onerivet.service.JWTService;
import com.onerivet.service.impl.UserDetailsServiceimpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter{
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	 private UserDetailsServiceimpl userDetaileServiceimpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authheader = request.getHeader("Authorization");
		String token=null;
		 String username = null;
		if(authheader!=null && authheader.startsWith("Bearer ")) {
			
			token=authheader.substring(7);
			username=jwtService.extractUsername(token);
			
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails UserByUsername = userDetaileServiceimpl.loadUserByUsername(username);
			if(jwtService.validateToken(token, UserByUsername)) {
				UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(username, null,UserByUsername.getAuthorities());
				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);
			
			
			}
			
			
		}
		filterChain.doFilter(request, response);
	}

}
