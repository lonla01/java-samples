package com.apptitude.employeemanager.repository;

import com.apptitude.employeemanager.dto.EmployeeDTO;
import com.apptitude.employeemanager.model.Department;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Remote Employee Repository implementation.
 * Calls the public JSONPlaceholder REST API to simulate a real remote service.
 */
public class RemoteEmployeeRepository implements EmployeeRepository {

    private static final String SERVICE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .executor(Executors.newVirtualThreadPerTaskExecutor())
            .build();

    @Override
    public EmployeeDTO create(EmployeeDTO employee) {
        try {
            String response = sendRequest("POST", SERVICE_URL, employee);
            return parseEmployee(response);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create employee via remote API", e);
        }
    }

    @Override
    public List<EmployeeDTO> findAll() {
        try {
            String response = sendRequest("GET", SERVICE_URL, null);
            return parseEmployeeList(response);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to fetch employees from remote API", e);
        }
    }

    @Override
    public Optional<EmployeeDTO> findById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        try {
            String response = sendRequest("GET", SERVICE_URL + "/" + id, null);
            EmployeeDTO employee = parseEmployee(response);
            return Optional.ofNullable(employee);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to fetch employee by id from remote API", e);
        }
    }

    @Override
    public List<EmployeeDTO> findByDepartment(Department department) {
        if (department == null) {
            return Collections.emptyList();
        }

        return findAll().stream()
                .filter(emp -> emp != null && emp.department() != null)
                .filter(emp -> emp.department() == department)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employee) {
        try {
            String response = sendRequest("PUT", SERVICE_URL + "/" + id, employee);
            return parseEmployee(response);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to update employee via remote API", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            return false;
        }

        try {
            String response = sendRequest("DELETE", SERVICE_URL + "/" + id, null);
            return response != null && !response.trim().isEmpty();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to delete employee via remote API", e);
        }
    }

    private static String sendRequest(String method, String endpoint, EmployeeDTO payload) throws IOException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json; charset=UTF-8");

        if (payload != null) {
            String requestBody = OBJECT_MAPPER.writeValueAsString(Map.of(
                    "id", payload.id(),
                    "name", payload.name(),
                    "department", payload.department(),
                    "skills", payload.skills() == null ? Collections.emptyList() : payload.skills()
            ));
            requestBuilder.method(method, HttpRequest.BodyPublishers.ofString(requestBody));
        } else {
            requestBuilder.method(method, HttpRequest.BodyPublishers.noBody());
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response;
        try {
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while calling remote employee API", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException ioException) {
                throw ioException;
            }
            throw new IOException("Unable to call remote employee API", cause);
        }

        if (response.statusCode() >= 400) {
            throw new IOException("HTTP " + response.statusCode() + ": " + response.body());
        }

        return response.body();
    }

    private static List<EmployeeDTO> parseEmployeeList(String json) throws IOException {
        JsonNode root = OBJECT_MAPPER.readTree(json);
        if (root == null || root.isNull()) {
            return Collections.emptyList();
        }

        List<EmployeeDTO> employees = new ArrayList<>();
        for (JsonNode node : root) {
            EmployeeDTO employee = parseEmployeeNode(node);
            if (employee != null) {
                employees.add(employee);
            }
        }
        return employees;
    }

    private static EmployeeDTO parseEmployee(String json) throws IOException {
        JsonNode root = OBJECT_MAPPER.readTree(json);
        if (root == null || root.isNull()) {
            return null;
        }
        return parseEmployeeNode(root);
    }

    private static EmployeeDTO parseEmployeeNode(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }

        Long id = node.hasNonNull("id") ? node.get("id").asLong() : null;
        String name = node.hasNonNull("name") ? node.get("name").asText() : null;
        String departmentName = node.path("company").path("name").asText();
        Department department = Department.fromString(departmentName);

        List<String> skills = new ArrayList<>();
        if (node.has("email") && !node.get("email").isNull()) {
            skills.add(node.get("email").asText());
        }
        if (node.has("phone") && !node.get("phone").isNull()) {
            skills.add(node.get("phone").asText());
        }
        if (node.has("website") && !node.get("website").isNull()) {
            skills.add(node.get("website").asText());
        }

        if (skills.isEmpty()) {
            skills = Arrays.asList("API", "REST", "HTTP");
        }

        return new EmployeeDTO(id, name, department, skills);
    }
}
