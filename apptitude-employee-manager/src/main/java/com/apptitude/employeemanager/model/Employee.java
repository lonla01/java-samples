package com.apptitude.employeemanager.model;

import java.util.List;

public record Employee(Long id, String name, Department department, List<String> skills) {

    public Employee(Long id, String name, Department department, List<String> skills) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.skills = List.copyOf(skills == null ? List.of() : skills);
    }

    public String departmentLabel() {
        return switch(this.department()) {
            case ENGINEERING -> "Technology";
            case MARKETING -> "Marketing";
            case SALES -> "Commercial";
            case FINANCE -> "Finance";
            case HR -> "Human Resources";
            case OPERATIONS -> "Production";
            case PLATFORM -> "Platform";
            case UNKNOWN -> "Unknown";
        };
    }
    
}
