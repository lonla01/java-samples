package com.apptitude.employeemanager.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldCreateEmployeeWithImmutableSkills() {
        Employee employee = new Employee(7L, "Alice Johnson", Department.ENGINEERING, List.of("Java", "Spring"));

        assertEquals(7L, employee.id());
        assertEquals("Alice Johnson", employee.name());
        assertEquals(Department.ENGINEERING, employee.department());
        assertEquals(List.of("Java", "Spring"), employee.skills());
        assertThrows(UnsupportedOperationException.class, () -> employee.skills().add("Kotlin"));
    }

    @Test
    void shouldNormalizeNullSkillsToEmptyList() {
        Employee employee = new Employee(8L, "Bob Smith", Department.FINANCE, null);

        assertNotNull(employee.skills());
        assertTrue(employee.skills().isEmpty());
    }
}
