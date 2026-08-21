package com.apptitude.employeemanager;

import com.apptitude.employeemanager.model.Department;
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
                Department.ENGINEERING,
                Arrays.asList("Java", "Spring")
        );

        assertEquals(1L, employee.id());
        assertEquals("Alice Johnson", employee.name());
        assertEquals(Department.ENGINEERING, employee.department());
        assertEquals(Arrays.asList("Java", "Spring"), employee.skills());
    }
}
