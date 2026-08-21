package com.apptitude.employeemanager.repository;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.model.Department;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Remote Employee Repository implementation.
 * Simulates calling a fake online REST service to manage employees.
 * Uses in-memory storage for demonstration purposes.
 */
public class LocalEmployeeRepository implements EmployeeRepository {

    private final Map<Long, EmployeeDTO> employees;
    private static final String SERVICE_URL = "https://api.example.com/v1/employees";

    public LocalEmployeeRepository() {
        this.employees = new LinkedHashMap<>();
        initializeFakeData();
    }

    /**
     * Initialize with fake employee data.
     * In a real scenario, this would fetch from the remote service.
     */
    private void initializeFakeData() {
        List<EmployeeDTO> initialData = Arrays.asList(
                new EmployeeDTO(1L, "Alice Johnson", Department.ENGINEERING,
                    Arrays.asList("Java", "Spring", "SQL")),
                new EmployeeDTO(2L, "Bob Smith", Department.ENGINEERING,
                    Arrays.asList("Java", "Docker", "AWS")),
                new EmployeeDTO(3L, "Carol Williams", Department.FINANCE,
                    Arrays.asList("Excel", "SQL", "Python")),
                new EmployeeDTO(4L, "David Brown", Department.ENGINEERING,
                    Arrays.asList("Python", "Kubernetes", "GCP")),
                new EmployeeDTO(5L, "Eva Martinez", Department.MARKETING,
                    Arrays.asList("Google Analytics", "SEO", "Content Marketing")),
                new EmployeeDTO(6L, "Frank Chen", Department.ENGINEERING,
                    Arrays.asList("JavaScript", "React", "Node.js")),
                new EmployeeDTO(7L, "Grace Lee", Department.HR,
                    Arrays.asList("Recruitment", "Training", "Employee Relations")),
                new EmployeeDTO(8L, "Henry Wilson", Department.FINANCE,
                    Arrays.asList("Accounting", "Financial Analysis", "Tax Planning")),
                new EmployeeDTO(9L, "Iris Thompson", Department.ENGINEERING,
                    Arrays.asList("C++", "Linux", "Systems Design")),
                new EmployeeDTO(10L, "Jack Davis", Department.SALES,
                    Arrays.asList("CRM", "Negotiation", "Sales Strategy")),
                new EmployeeDTO(11L, "Karen Miller", Department.ENGINEERING,
                    Arrays.asList("Java", "Microservices", "MongoDB")),
                new EmployeeDTO(12L, "Leo Anderson", Department.OPERATIONS,
                    Arrays.asList("Project Management", "Process Improvement", "Supply Chain")),
                new EmployeeDTO(13L, "Monica Garcia", Department.ENGINEERING,
                    Arrays.asList("TypeScript", "Angular", "REST APIs")),
                new EmployeeDTO(14L, "Nathan Taylor", Department.FINANCE,
                    Arrays.asList("Budgeting", "Forecasting", "Financial Reporting")),
                new EmployeeDTO(15L, "Olivia Jackson", Department.MARKETING,
                    Arrays.asList("Social Media", "Brand Management", "Event Planning")),
                new EmployeeDTO(16L, "Patrick White", Department.ENGINEERING,
                    Arrays.asList("Go", "Rust", "Cloud Architecture")),
                new EmployeeDTO(17L, "Quinn Rodriguez", Department.HR,
                    Arrays.asList("Benefits Administration", "Payroll", "Compliance")),
                new EmployeeDTO(18L, "Rachel Harris", Department.ENGINEERING,
                    Arrays.asList("Java", "Spring Boot", "PostgreSQL")),
                new EmployeeDTO(19L, "Samuel Clark", Department.SALES,
                    Arrays.asList("Account Management", "Client Relations", "Revenue Growth")),
                new EmployeeDTO(20L, "Tina Lewis", Department.ENGINEERING,
                    Arrays.asList("Python", "Data Science", "Machine Learning")),
                new EmployeeDTO(21L, "Uriel Walker", Department.OPERATIONS,
                    Arrays.asList("Quality Assurance", "Testing", "Documentation")),
                new EmployeeDTO(22L, "Vanessa Hall", Department.MARKETING,
                    Arrays.asList("Email Marketing", "Marketing Automation", "Analytics")),
                new EmployeeDTO(23L, "William Allen", Department.ENGINEERING,
                    Arrays.asList("Java", "Jenkins", "DevOps")),
                new EmployeeDTO(24L, "Ximena Young", Department.FINANCE,
                    Arrays.asList("Risk Management", "Internal Audit", "Compliance")),
                new EmployeeDTO(25L, "Yuki Hernandez", Department.ENGINEERING,
                    Arrays.asList("Swift", "iOS Development", "Mobile Architecture"))
        );

        initialData.forEach(emp -> employees.put(emp.id(), emp));
    }

    @Override
    public EmployeeDTO create(EmployeeDTO employee) {
        // Simulate remote POST call
        System.out.println("Simulating POST to " + SERVICE_URL);
        if (employee.id() == null) {
            Long nextId = employees.keySet().stream()
                    .mapToLong(Long::longValue)
                    .max()
                    .orElse(0L) + 1;
            EmployeeDTO created = new EmployeeDTO(nextId, employee.name(), employee.department(), employee.skills());
            employees.put(nextId, created);
            return created;
        }
        employees.put(employee.id(), employee);
        return employee;
    }

    @Override
    public List<EmployeeDTO> findAll() {
        // Simulate remote GET call
        System.out.println("Simulating GET from " + SERVICE_URL);
        return new ArrayList<>(employees.values());
    }

    @Override
    public Optional<EmployeeDTO> findById(Long id) {
        // Simulate remote GET call with ID
        System.out.println("Simulating GET from " + SERVICE_URL + "/" + id);
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public List<EmployeeDTO> findByDepartment(Department department) {
        // Simulate remote GET call with filter
        System.out.println("Simulating GET from " + SERVICE_URL + "?department=" + department);
        return employees.values().stream()
                .filter(emp -> emp.department() == department)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employee) {
        // Simulate remote PUT call
        System.out.println("Simulating PUT to " + SERVICE_URL + "/" + id);
        EmployeeDTO updated = new EmployeeDTO(id, employee.name(), employee.department(), employee.skills());
        employees.put(id, updated);
        return updated;
    }

    @Override
    public boolean delete(Long id) {
        // Simulate remote DELETE call
        System.out.println("Simulating DELETE from " + SERVICE_URL + "/" + id);
        return employees.remove(id) != null;
    }
}
