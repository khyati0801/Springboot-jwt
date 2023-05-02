package com.onerivet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onerivet.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	public Employee findByName(String name);

}
