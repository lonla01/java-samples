package com.apptitude.employeemanager;

import com.apptitude.employeemanager.model.Department;
import com.apptitude.employeemanager.model.Employee;

import java.util.List;

public class App {

    public static void main(String[] args) {
        var employees = List.of(
                new Employee(1L, "Alice Johnson", Department.ENGINEERING,
                        List.of("Java", "Spring", "SQL")),
                new Employee(2L, "Bob Smith", Department.ENGINEERING,
                        List.of("Java", "Docker", "AWS")),
                new Employee(3L, "Carol Williams", Department.FINANCE,
                        List.of("Excel", "SQL", "Python"))
        );

        employees.forEach(System.out::println);
    }
}
