package com.onerivet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.onerivet.entity.Employee;
import com.onerivet.repository.EmployeeRepository;
@Service
public class UserDetailsServiceimpl implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeerepository;
	 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Employee employee = employeerepository.findByName(username);
		System.out.println(username);
		System.out.println(employee.getName());
		return new  UserDetailsImpl(employee);
		
	}

}
