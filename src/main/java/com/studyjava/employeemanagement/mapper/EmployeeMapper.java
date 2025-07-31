package com.studyjava.employeemanagement.mapper;

import com.studyjava.employeemanagement.dto.EmployeeDto;
import com.studyjava.employeemanagement.model.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
       return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }
    
}

