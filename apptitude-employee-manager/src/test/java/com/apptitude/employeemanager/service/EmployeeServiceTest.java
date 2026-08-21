package com.apptitude.employeemanager.service;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.model.Department;
import com.apptitude.employeemanager.repository.LocalEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService service;

    @BeforeEach
    void setUp() {
        service = new EmployeeService(new LocalEmployeeRepository());
    }

    @Test
    void shouldCreateEmployeeWhenDataIsValid() {
        EmployeeDTO toCreate = new EmployeeDTO(null, "Frank Green", Department.ENGINEERING, List.of("Java", "Testing"));

        EmployeeDTO created = service.createEmployee(toCreate);

        assertNotNull(created.id());
        assertEquals("Frank Green", created.name());
        assertEquals(Department.ENGINEERING, created.department());
    }

    @Test
    void shouldRejectEmployeeWithBlankName() {
        EmployeeDTO invalidEmployee = new EmployeeDTO(null, "   ", Department.ENGINEERING, List.of("Java"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createEmployee(invalidEmployee));

        assertEquals("Employee name cannot be empty", ex.getMessage());
    }

    @Test
    void shouldRejectEmployeeWithBlankDepartment() {
        EmployeeDTO invalidEmployee = new EmployeeDTO(null, "Jane Doe", null, List.of("Java"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createEmployee(invalidEmployee));

        assertEquals("Employee department cannot be empty", ex.getMessage());
    }

    @Test
    void shouldReturnEmployeeById() {
        Optional<EmployeeDTO> employee = service.getEmployeeById(3L);

        assertTrue(employee.isPresent());
        assertEquals("Carol Williams", employee.get().name());
    }

    @Test
    void shouldRejectInvalidEmployeeId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.getEmployeeById(0L));

        assertEquals("Invalid employee ID", ex.getMessage());
    }

    @Test
    void shouldFilterEmployeesByDepartment() {
        List<EmployeeDTO> employees = service.getEmployeesByDepartment(Department.FINANCE);

        assertFalse(employees.isEmpty());
        assertTrue(employees.stream().allMatch(emp -> Department.FINANCE.equals(emp.department())));
    }

    @Test
    void shouldCountEmployeesPerDepartment() {
        Map<Department, Long> counts = service.getEmployeeCountByDepartment();

        assertFalse(counts.isEmpty());
        assertTrue(counts.containsKey(Department.ENGINEERING));
        assertTrue(counts.get(Department.ENGINEERING) > 0);
    }

    @Test
    void shouldSearchEmployeeBySkillCaseInsensitive() {
        List<EmployeeDTO> results = service.searchBySkill("java");

        assertFalse(results.isEmpty());
        assertTrue(results.stream().allMatch(emp -> emp.skills().stream()
                .anyMatch(skill -> skill.toLowerCase().contains("java"))));
    }

    @Test
    void shouldUpdateExistingEmployee() {
        EmployeeDTO updated = new EmployeeDTO(1L, "Alice Updated", Department.PLATFORM, List.of("Java", "Kubernetes"));

        EmployeeDTO result = service.updateEmployee(1L, updated);

        assertEquals("Alice Updated", result.name());
        assertEquals(Department.PLATFORM, result.department());
    }

    @Test
    void shouldRejectUpdateForMissingEmployee() {
        EmployeeDTO updated = new EmployeeDTO(999L, "Missing User", Department.ENGINEERING, List.of("Java"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.updateEmployee(999L, updated));

        assertEquals("Employee with ID 999 not found", ex.getMessage());
    }

    @Test
    void shouldDeleteEmployee() {
        boolean deleted = service.deleteEmployee(4L);

        assertTrue(deleted);
    }

    @Test
    void shouldRejectDeleteForInvalidId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.deleteEmployee(-1L));

        assertEquals("Invalid employee ID", ex.getMessage());
    }
}
