package com.em.employeeservice.service;

import com.em.employeeservice.dto.EmployeeRequestDTO;
import com.em.employeeservice.dto.EmployeeResponseDTO;
import com.em.employeeservice.exception.EmailAlreadyExistsException;
import com.em.employeeservice.exception.EmployeeNotFoundException;
import com.em.employeeservice.mapper.EmployeeMapper;
import com.em.employeeservice.model.Employee;
import com.em.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponseDTO> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(EmployeeMapper::toDTO).toList();

    }

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        if (employeeRepository.existsByEmail(employeeRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("An employee with this email already exists: " +
                    employeeRequestDTO.getEmail());
        }
        Employee newEmployee = employeeRepository.save(
                EmployeeMapper.toEntityModel(employeeRequestDTO));

        return EmployeeMapper.toDTO(newEmployee);

    }


    public EmployeeResponseDTO updateEmployee(UUID id, EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        if (employeeRepository.existsByEmail(employeeRequestDTO.getEmail())) {

            throw new EmailAlreadyExistsException("An employee with this email already exists: " +
                    employeeRequestDTO.getEmail());
        }

        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setName(employeeRequestDTO.getName());
        employee.setAddress(employeeRequestDTO.getAddress());
        employee.setDateOfBirth(LocalDate.parse(employeeRequestDTO.getDateOfBirth()));

        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toDTO(updatedEmployee);


    }
}
