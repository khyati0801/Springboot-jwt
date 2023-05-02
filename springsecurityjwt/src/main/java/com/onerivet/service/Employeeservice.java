package com.onerivet.service;

import java.util.List;

import com.onerivet.entity.Employee;


public interface Employeeservice {
	
	public String save(Employee employee);
	
	public List<Employee> getAll();
	
	public Employee getById(int id);
	

}
