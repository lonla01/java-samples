package com.apptitude.employeemanager;

import com.apptitude.employeemanager.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void employeeShouldExposeItsBasicInformation() {
        Employee employee = new Employee(
                1L,
                "Alice Johnson",
                "Engineering",
                Arrays.asList("Java", "Spring")
        );

        assertEquals(1L, employee.getId());
        assertEquals("Alice Johnson", employee.getName());
        assertEquals("Engineering", employee.getDepartment());
        assertEquals(Arrays.asList("Java", "Spring"), employee.getSkills());
    }
}
