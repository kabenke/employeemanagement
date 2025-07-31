package com.studyjava.employeemanagement.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyjava.employeemanagement.dto.EmployeeDto;
import com.studyjava.employeemanagement.service.EmployeeService;

import lombok.AllArgsConstructor;




@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
   
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long empLoyeeId) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(empLoyeeId);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

}
