package com.apptitude.employeemanager.dto;

import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for Employee.
 * Used to transfer employee data between API and service layers.
 */
public class EmployeeDTO {

    private Long id;
    private String name;
    private String department;
    private List<String> skills;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String name, String department, List<String> skills) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(department, that.department) &&
                Objects.equals(skills, that.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, department, skills);
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", skills=" + skills +
                '}';
    }
}
