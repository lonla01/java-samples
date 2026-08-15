package com.apptitude.employeemanager.api;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.service.EmployeeService;
import java.util.*;

/**
 * Employee REST API interface.
 * Demonstrates RESTful endpoints for Employee CRUD operations.
 * 
 * In a production environment with Spring Boot, these would be
 * actual @RestController endpoints. This serves as a simulation
 * of the API contract.
 */
public class EmployeeAPI {

    private final EmployeeService employeeService;

    public EmployeeAPI(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * GET /api/v1/employees
     * Retrieve all employees.
     */
    public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            return new ApiResponse<>(200, "Employees retrieved successfully", employees);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving employees: " + e.getMessage(), null);
        }
    }

    /**
     * GET /api/v1/employees/{id}
     * Retrieve an employee by ID.
     */
    public ApiResponse<EmployeeDTO> getEmployeeById(Long id) {
        try {
            Optional<EmployeeDTO> employee = employeeService.getEmployeeById(id);
            if (employee.isPresent()) {
                return new ApiResponse<>(200, "Employee found", employee.get());
            } else {
                return new ApiResponse<>(404, "Employee not found", null);
            }
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(400, e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving employee: " + e.getMessage(), null);
        }
    }

    /**
     * GET /api/v1/employees?department={department}
     * Retrieve employees by department.
     */
    public ApiResponse<List<EmployeeDTO>> getEmployeesByDepartment(String department) {
        try {
            List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment(department);
            return new ApiResponse<>(200, "Employees retrieved successfully", employees);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(400, e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving employees: " + e.getMessage(), null);
        }
    }

    /**
     * GET /api/v1/employees/search/skill?skill={skill}
     * Search employees by skill.
     */
    public ApiResponse<List<EmployeeDTO>> searchBySkill(String skill) {
        try {
            List<EmployeeDTO> employees = employeeService.searchBySkill(skill);
            return new ApiResponse<>(200, "Search completed", employees);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(400, e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error searching employees: " + e.getMessage(), null);
        }
    }

    /**
     * GET /api/v1/employees/stats/departments
     * Get employee count by department.
     */
    public ApiResponse<Map<String, Long>> getEmployeeCountByDepartment() {
        try {
            Map<String, Long> stats = employeeService.getEmployeeCountByDepartment();
            return new ApiResponse<>(200, "Statistics retrieved", stats);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error retrieving statistics: " + e.getMessage(), null);
        }
    }

    /**
     * POST /api/v1/employees
     * Create a new employee.
     */
    public ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO employee) {
        try {
            EmployeeDTO created = employeeService.createEmployee(employee);
            return new ApiResponse<>(201, "Employee created successfully", created);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(400, e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error creating employee: " + e.getMessage(), null);
        }
    }

    /**
     * PUT /api/v1/employees/{id}
     * Update an existing employee.
     */
    public ApiResponse<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employee) {
        try {
            EmployeeDTO updated = employeeService.updateEmployee(id, employee);
            return new ApiResponse<>(200, "Employee updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(400, e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error updating employee: " + e.getMessage(), null);
        }
    }

    /**
     * DELETE /api/v1/employees/{id}
     * Delete an employee.
     */
    public ApiResponse<Void> deleteEmployee(Long id) {
        try {
            boolean deleted = employeeService.deleteEmployee(id);
            if (deleted) {
                return new ApiResponse<>(204, "Employee deleted successfully", null);
            } else {
                return new ApiResponse<>(404, "Employee not found", null);
            }
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(400, e.getMessage(), null);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting employee: " + e.getMessage(), null);
        }
    }

    /**
     * Generic API Response wrapper.
     */
    public record ApiResponse<T>(int statusCode, String message, T data, long timestamp) {
        public ApiResponse(int statusCode, String message, T data) {
            this(statusCode, message, data, System.currentTimeMillis());
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        public T getData() {
            return data;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
