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
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail()); 
        if (employeeDto.getId() != null) {
            employee.setId(employeeDto.getId());
        }
        return employee;
    }

    
}

