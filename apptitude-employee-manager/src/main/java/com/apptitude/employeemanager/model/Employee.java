package com.apptitude.employeemanager.model;

import java.util.List;

public record Employee(Long id, String name, String department, List<String> skills) {

    public Employee {
        skills = List.copyOf(skills == null ? List.of() : skills);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public List<String> getSkills() {
        return skills;
    }
}
