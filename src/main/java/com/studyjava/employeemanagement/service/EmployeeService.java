package com.studyjava.employeemanagement.service;

import com.studyjava.employeemanagement.dto.EmployeeDto;

public interface EmployeeService {

   EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long id);
}
