package com.apptitude.employeemanager.repository;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LocalEmployeeRepositoryTest {

    private LocalEmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new LocalEmployeeRepository();
    }

    @Test
    void shouldInitializeWithSeedData() {
        List<EmployeeDTO> employees = repository.findAll();

        assertFalse(employees.isEmpty());
        assertEquals(25, employees.size());
        assertTrue(employees.stream().allMatch(emp -> emp.id() != null));
    }

    @Test
    void shouldCreateEmployeeWhenIdIsMissing() {
        EmployeeDTO newEmployee = new EmployeeDTO(null, "Zoe Kim", Department.ENGINEERING, List.of("Java", "Kubernetes"));

        EmployeeDTO created = repository.create(newEmployee);

        assertNotNull(created.id());
        assertEquals(26L, created.id());
        assertEquals("Zoe Kim", created.name());
        assertEquals(Department.ENGINEERING, created.department());
    }

    @Test
    void shouldFindEmployeeById() {
        Optional<EmployeeDTO> employee = repository.findById(1L);

        assertTrue(employee.isPresent());
        assertEquals("Alice Johnson", employee.get().name());
        assertEquals(Department.ENGINEERING, employee.get().department());
    }

    @Test
    void shouldReturnEmptyOptionalWhenEmployeeDoesNotExist() {
        Optional<EmployeeDTO> employee = repository.findById(999L);

        assertTrue(employee.isEmpty());
    }

    @Test
    void shouldFilterEmployeesByDepartmentCaseInsensitive() {
        List<EmployeeDTO> engineers = repository.findByDepartment(Department.ENGINEERING);

        assertFalse(engineers.isEmpty());
        assertTrue(engineers.stream().allMatch(emp -> Department.ENGINEERING.equals(emp.department())));
    }

    @Test
    void shouldUpdateExistingEmployee() {
        EmployeeDTO updated = new EmployeeDTO(1L, "Alice Updated", Department.PLATFORM, List.of("Java", "Kubernetes"));

        EmployeeDTO result = repository.update(1L, updated);

        assertEquals(1L, result.id());
        assertEquals("Alice Updated", result.name());
        assertEquals(Department.PLATFORM, result.department());
        assertEquals(List.of("Java", "Kubernetes"), result.skills());
    }

    @Test
    void shouldDeleteEmployeeById() {
        boolean deleted = repository.delete(2L);

        assertTrue(deleted);
        assertTrue(repository.findById(2L).isEmpty());
    }

    @Test
    void shouldReturnFalseWhenDeletingMissingEmployee() {
        boolean deleted = repository.delete(999L);

        assertFalse(deleted);
    }
}
