package com.apptitude.employeemanager.repository;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RemoteEmployeeRepositoryTest {

    private final RemoteEmployeeRepository repository = new RemoteEmployeeRepository();

    @Test
    void shouldFetchAllEmployeesFromRemoteApi() {
        List<EmployeeDTO> employees = repository.findAll();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertTrue(employees.stream().allMatch(emp -> emp.getId() != null));
    }

    @Test
    void shouldFetchEmployeeByIdFromRemoteApi() {
        Optional<EmployeeDTO> employee = repository.findById(1L);

        assertTrue(employee.isPresent());
        assertEquals(1L, employee.get().getId());
        assertEquals("Leanne Graham", employee.get().getName());
        assertNotNull(employee.get().getSkills());
    }

    @Test
    void shouldFilterEmployeesByDepartment() {
        List<EmployeeDTO> employees = repository.findByDepartment("Romaguera-Crona");

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertTrue(employees.stream().allMatch(emp -> "Romaguera-Crona".equalsIgnoreCase(emp.getDepartment())));
    }
}
