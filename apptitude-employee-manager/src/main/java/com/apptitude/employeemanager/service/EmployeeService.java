package com.apptitude.employeemanager.service;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.model.Employee;
import com.apptitude.employeemanager.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Employee Service - Business logic layer.
 * Handles CRUD operations and business rules for employees.
 */
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a new employee.
     */
    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee department cannot be empty");
        }
        return repository.create(employee);
    }

    /**
     * Retrieve all employees.
     */
    public List<EmployeeDTO> getAllEmployees() {
        return repository.findAll();
    }

    /**
     * Retrieve an employee by ID.
     */
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        return repository.findById(id);
    }

    /**
     * Retrieve employees by department.
     */
    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        return repository.findByDepartment(department);
    }

    /**
     * Get count of employees in each department.
     */
    public java.util.Map<String, Long> getEmployeeCountByDepartment() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(EmployeeDTO::getDepartment, Collectors.counting()));
    }

    /**
     * Update an existing employee.
     */
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employee) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        if (!repository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found");
        }
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        return repository.update(id, employee);
    }

    /**
     * Delete an employee.
     */
    public boolean deleteEmployee(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        return repository.delete(id);
    }

    /**
     * Search employees by skill.
     */
    public List<EmployeeDTO> searchBySkill(String skill) {
        if (skill == null || skill.trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name cannot be empty");
        }
        String searchSkill = skill.toLowerCase();
        return repository.findAll().stream()
                .filter(emp -> emp.getSkills() != null && 
                              emp.getSkills().stream()
                                  .anyMatch(s -> s.toLowerCase().contains(searchSkill)))
                .collect(Collectors.toList());
    }

    /**
     * Asynchronously find and employee by ID. 
     */
    public CompletableFuture<EmployeeDTO> findEmployeeByIdAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> getEmployeeById(id))
        .thenApply(opt -> opt
            .orElseThrow(
                () -> new IllegalArgumentException("Employee with ID " + id + " not found")
            )
        );
    }

    public CompletableFuture<Employee> ___findEmployeeByIdAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<EmployeeDTO> employeeDTO = getEmployeeById(id);
            if (employeeDTO.isPresent()) {
                EmployeeDTO dto = employeeDTO.get();
                return new Employee(dto.getId(), dto.getName(), dto.getDepartment(), dto.getSkills());
            } else {
                throw new IllegalArgumentException("Employee with ID " + id + " not found");
            }
        });
    }
}
