package com.rfa.employee.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rfa.employee.exception.DataNotFoundException;
import com.rfa.employee.model.Employee;
import com.rfa.employee.repository.EmployeeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Employee") //semua dalem akan masuk ke tag employee
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	//kita kasitau mau masukin dia ke tags yang mana
	@ApiOperation(
			value = "API to retrieve all employee's data",
			notes = "Return data with JSON format",
			tags  = "Get Data API")
	@GetMapping("/emp")
	public List<Employee> GetAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@GetMapping("emp/{id}")
	public ResponseEntity<Employee> GetEmployeeById(@PathVariable(value = "id") Long employeeId) throws DataNotFoundException{
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new DataNotFoundException("Employee not found for this id :: " + employeeId));
		
		return ResponseEntity.ok().body(employee);
	}
	
	@GetMapping("emp/{name}/{dob}")
	public ResponseEntity<List<Employee>> GetEmployeeByName(@PathVariable(value = "name") String employeeName, @PathVariable(value = "dob") Date dob){
		
		Employee employees = new Employee();
		
		employees.setName(employeeName);
		employees.setDob(dob);
		
		Example<Employee> employeesExample = Example.of(employees);
		
		List<Employee> employeesReturn = employeeRepository.findAll(employeesExample);
		
		return ResponseEntity.ok().body(employeesReturn);
	}
	
	@ApiOperation(
			value = "Insert employee's data",
			notes = "Insert employee's data based on data Id",
			tags  = "Data Manipulation API")
	@PostMapping("/emp")
	public Employee InsertEmployee(@Valid @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@ApiOperation(
			value = "Update employee's data",
			notes = "Update employee's data based on data Id",
			tags  = "Data Manipulation API")
	
	@PutMapping("/emp/{id}")
	public ResponseEntity<Employee> UpdateEmployee(@Valid @RequestBody Employee employeeRequest, @PathVariable(value = "id") Long employeeId) throws DataNotFoundException {
		
		 Employee employee = employeeRepository.findById(employeeId)
			        .orElseThrow(() -> new DataNotFoundException("Employee not found for this id :: " + employeeId));
		
			employee.setAddress(employeeRequest.getAddress());
			employee.setAge(employeeRequest.getAge());
			employee.setName(employeeRequest.getName());
			employee.setDob(employeeRequest.getDob());
			employee.setPhone(employeeRequest.getPhone());
			employee.setSalary(employeeRequest.getSalary());
			employee.setId_div(employeeRequest.getId_div());
			
			final Employee updateEmployee = employeeRepository.save(employee);

		
		return ResponseEntity.ok().body(updateEmployee);
	}
	
	@ApiOperation(
			value = "Delete employee's data",
			notes = "Delete employee's data based on data Id",
			tags  = "Data Manipulation API")
	
    @DeleteMapping("/emp/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
         throws DataNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
       .orElseThrow(() -> new DataNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	
}
