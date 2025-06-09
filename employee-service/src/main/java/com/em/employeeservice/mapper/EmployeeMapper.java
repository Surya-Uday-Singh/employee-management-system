package com.em.employeeservice.mapper;

import com.em.employeeservice.dto.EmployeeRequestDTO;
import com.em.employeeservice.dto.EmployeeResponseDTO;
import com.em.employeeservice.model.Employee;

import java.time.LocalDate;

public class EmployeeMapper {
    public static EmployeeResponseDTO toDTO ( Employee employee) {
        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        employeeDTO.setId(employee.getId().toString());
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setDateOfBirth(employee.getDateOfBirth().toString());
        return employeeDTO;
    }

    public static Employee toEntityModel ( EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();
        employee.setName(employeeRequestDTO.getName());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setAddress(employeeRequestDTO.getAddress());
        employee.setDateOfBirth(LocalDate.parse(employeeRequestDTO.getDateOfBirth()));
        employee.setRegistrationDate(LocalDate.parse(employeeRequestDTO.getRegisteredDate()));
        return employee;

    }

}
