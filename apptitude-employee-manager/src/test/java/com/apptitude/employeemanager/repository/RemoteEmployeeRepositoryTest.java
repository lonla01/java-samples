package com.apptitude.employeemanager.repository;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.model.Department;
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
        assertTrue(employees.stream().allMatch(emp -> emp.id() != null));
    }

    @Test
    void shouldFetchEmployeeByIdFromRemoteApi() {
        Optional<EmployeeDTO> employee = repository.findById(1L);

        assertTrue(employee.isPresent());
        assertEquals(1L, employee.get().id());
        assertEquals("Leanne Graham", employee.get().name());
        assertNotNull(employee.get().skills());
    }

    @Test
    void shouldFilterEmployeesByDepartment() {
        List<EmployeeDTO> employees = repository.findByDepartment(Department.UNKNOWN);

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertTrue(employees.stream().allMatch(emp -> Department.UNKNOWN.equals(emp.department())));
    }
}
