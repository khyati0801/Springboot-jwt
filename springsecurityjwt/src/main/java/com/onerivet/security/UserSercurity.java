package com.onerivet.security;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.onerivet.filter.JWTAuthFilter;
import com.onerivet.service.impl.UserDetailsServiceimpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class UserSercurity {

	@Autowired
	private JWTAuthFilter authfilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		 return http.cors().and().csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/api/employee/login", "/api/employee/").permitAll()
		.anyRequest().authenticated().and()
		//.authenticationProvider(authenticationProvider())
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authenticationProvider(authenticationProvider())
		.addFilterBefore(authfilter, UsernamePasswordAuthenticationFilter.class)
        .build();

	}

	@Bean
	public AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(UserDetailsService());
		authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

		return authenticationProvider;

	}

	@Bean
	public PasswordEncoder PasswordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService UserDetailsService() {
		return new UserDetailsServiceimpl();

//		return UserDetailsService() ;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();

	}

}
