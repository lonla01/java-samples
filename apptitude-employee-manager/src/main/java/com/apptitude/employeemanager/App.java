package com.apptitude.employeemanager;

import com.apptitude.employeemanager.model.Employee;

import java.util.List;

public class App {

    public static void main(String[] args) {
        var employees = List.of(
                new Employee(1L, "Alice Johnson", "Engineering",
                        List.of("Java", "Spring", "SQL")),
                new Employee(2L, "Bob Smith", "Engineering",
                        List.of("Java", "Docker", "AWS")),
                new Employee(3L, "Carol Williams", "Finance",
                        List.of("Excel", "SQL", "Python"))
        );

        employees.forEach(System.out::println);
    }
}
