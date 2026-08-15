package com.apptitude.employeemanager.dto;

import java.util.List;

/**
 * Data Transfer Object for Employee.
 * Used to transfer employee data between API and service layers.
 */
public record EmployeeDTO(Long id, String name, String department, List<String> skills) {

    public EmployeeDTO {
        skills = List.copyOf(skills == null ? List.of() : skills);
    }
}
