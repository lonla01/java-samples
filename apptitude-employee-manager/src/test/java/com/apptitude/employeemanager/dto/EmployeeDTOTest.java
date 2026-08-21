package com.apptitude.employeemanager.dto;

import com.apptitude.employeemanager.model.Department;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDTOTest {

    @Test
    void shouldCreateEmployeeDtoWithProvidedValues() {
        EmployeeDTO dto = new EmployeeDTO(11L, "Carol Williams", Department.FINANCE, List.of("Excel", "SQL"));

        assertEquals(11L, dto.id());
        assertEquals("Carol Williams", dto.name());
        assertEquals(Department.FINANCE, dto.department());
        assertEquals(List.of("Excel", "SQL"), dto.skills());
    }

    @Test
    void shouldNormalizeNullSkillsToEmptyList() {
        EmployeeDTO dto = new EmployeeDTO(12L, "Diana Ross", Department.MARKETING, null);

        assertNotNull(dto.skills());
        assertTrue(dto.skills().isEmpty());
    }
}
