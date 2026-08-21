package com.apptitude.employeemanager.dto;

import com.apptitude.employeemanager.model.Department;

import java.util.List;

/**
 * Data Transfer Object for Employee.
 * Used to transfer employee data between API and service layers.
 */
public record EmployeeDTO(Long id, String name, Department department, List<String> skills) {

    public EmployeeDTO {
        skills = List.copyOf(skills == null ? List.of() : skills);
    }
    
}
