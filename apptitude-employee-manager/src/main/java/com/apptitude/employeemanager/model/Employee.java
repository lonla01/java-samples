package com.apptitude.employeemanager.model;

import java.util.List;

public class Employee {

    private final Long id;
    private final String name;
    private final String department;
    private final List<String> skills;

    public Employee(Long id, String name, String department, List<String> skills) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.skills = skills;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", skills=" + skills +
                '}';
    }
}
