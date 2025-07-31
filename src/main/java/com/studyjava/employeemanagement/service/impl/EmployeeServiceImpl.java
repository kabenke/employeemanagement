package com.studyjava.employeemanagement.service.impl;


import org.springframework.stereotype.Service;

import com.studyjava.employeemanagement.dto.EmployeeDto;
import com.studyjava.employeemanagement.exception.ResourcesNotFoundException;
import com.studyjava.employeemanagement.mapper.EmployeeMapper;
import com.studyjava.employeemanagement.model.Employee;
import com.studyjava.employeemanagement.repository.EmployeeRepository;
import com.studyjava.employeemanagement.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private  EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }
    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Employee not found with id: " + id));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }
}
