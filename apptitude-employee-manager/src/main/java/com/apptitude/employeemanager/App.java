package com.apptitude.employeemanager;

import com.apptitude.employeemanager.model.Employee;

import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "Alice Johnson", "Engineering",
                        Arrays.asList("Java", "Spring", "SQL")),
                new Employee(2L, "Bob Smith", "Engineering",
                        Arrays.asList("Java", "Docker", "AWS")),
                new Employee(3L, "Carol Williams", "Finance",
                        Arrays.asList("Excel", "SQL", "Python"))
        );

        employees.forEach(System.out::println);
    }
}
