package com.apptitude.employeemanager.service;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.model.Department;
import com.apptitude.employeemanager.model.Employee;
import com.apptitude.employeemanager.repository.EmployeeRepository;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Employee Service - Business logic layer.
 * Handles CRUD operations and business rules for employees.
 */
public class EmployeeService {

    private static final ExecutorService VIRTUAL_THREAD_EXECUTOR =
            Executors.newVirtualThreadPerTaskExecutor();
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a new employee.
     */
    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        if (employee.name() == null || employee.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (employee.department() == null) {
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
    public List<EmployeeDTO> getEmployeesByDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        return repository.findByDepartment(department);
    }

    /**
     * Get count of employees in each department.
     */
    public Map<Department, Long> getEmployeeCountByDepartment() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(EmployeeDTO::department, Collectors.counting()));
    }

    /**
     * Update an existing employee.
     */
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employee) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found");
        }
        if (employee.name() == null || employee.name().trim().isEmpty()) {
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
        String searchSkill = skill.toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(emp -> emp.skills() != null &&
                              emp.skills().stream()
                                  .anyMatch(s -> s.toLowerCase(Locale.ROOT).contains(searchSkill)))
                .collect(Collectors.toList());
    }

    /**
     * Asynchronously find and employee by ID. 
     */
    public CompletableFuture<EmployeeDTO> findEmployeeByIdAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> getEmployeeById(id), VIRTUAL_THREAD_EXECUTOR)
                .thenApply(opt -> opt.orElseThrow(
                        () -> new IllegalArgumentException("Employee with ID " + id + " not found")
                ));
    }

    public CompletableFuture<Employee> ___findEmployeeByIdAsync(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<EmployeeDTO> employeeDTO = getEmployeeById(id);
            if (employeeDTO.isPresent()) {
                var dto = employeeDTO.get();
                return new Employee(dto.id(), dto.name(), dto.department(), dto.skills());
            }
            throw new IllegalArgumentException("Employee with ID " + id + " not found");
        }, VIRTUAL_THREAD_EXECUTOR);
    }
}
