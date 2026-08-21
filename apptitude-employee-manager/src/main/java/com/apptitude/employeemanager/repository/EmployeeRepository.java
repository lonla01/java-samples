package com.apptitude.employeemanager.repository;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.model.Department;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee data access.
 * Defines CRUD operations for managing employees.
 */
public interface EmployeeRepository {

    /**
     * Create a new employee.
     */
    EmployeeDTO create(EmployeeDTO employee);

    /**
     * Retrieve all employees.
     */
    List<EmployeeDTO> findAll();

    /**
     * Retrieve an employee by ID.
     */
    Optional<EmployeeDTO> findById(Long id);

    /**
     * Retrieve employees by department.
     */
    List<EmployeeDTO> findByDepartment(Department department);

    /**
     * Update an existing employee.
     */
    EmployeeDTO update(Long id, EmployeeDTO employee);

    /**
     * Delete an employee by ID.
     */
    boolean delete(Long id);
}
