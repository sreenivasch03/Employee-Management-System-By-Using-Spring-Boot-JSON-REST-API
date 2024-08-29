package com.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.entity.Employee;
import com.employee.repository.EmployeeRepo;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepo employeeRepo;
	
	
	@PostMapping("/api/employeeRegistration")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<Employee> (employeeRepo.save(employee),HttpStatus.CREATED);
	}
	
	@GetMapping("/api/getEmployeeDetails")
	public ResponseEntity<List<Employee>> getEmployeeDetails() {
		
		return new ResponseEntity<>( employeeRepo.findAll(),HttpStatus.OK);
	}
	
	
	@GetMapping("/api/getEmployeeDetailsById/{id}")
	public ResponseEntity<Employee> getEmployeeDetailsById(@PathVariable int id) {
		
		Optional<Employee> emp = employeeRepo.findById(id);
		
		if(emp.isPresent()) {
			return new ResponseEntity<>( emp.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/api/deleteEmployeeById/{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable int id) {
		
		Optional<Employee> emp = employeeRepo.findById(id);
		
		if(emp.isPresent()) {
			employeeRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/api/updateEmployeeDetailsById/{id}")
	public ResponseEntity<Employee> updateEmployeeDetailsById(@PathVariable int id ,@RequestBody Employee employee) {
		
		Optional<Employee> emp = employeeRepo.findById(id);
		
		if(emp.isPresent()) {
			emp.get().setEmployeeSalary(employee.getEmployeeSalary());
			emp.get().setEmployeeDeptno(employee.getEmployeeDeptno());
			return new ResponseEntity<>(employeeRepo.save(emp.get()),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
