# Apptitude Employee Manager

A fictitious learning project using **Apptitude** as the business context.

> This project is an independent educational exercise and is not an official Apptitude application.

## Purpose

The same application will evolve progressively as we move through Java versions:

- Java 8
- Java 11
- Java 17
- Java 21
- Java 25

We will keep one codebase and use Git tags to mark major Java-version milestones.

## Current milestone

**Java 11**

Current focus:
- Collections
- Lambdas
- Streams
- Optional
- Maven
- Unit testing

## Build

```bash
mvn clean test
```

## Run

```bash
mvn package
java -cp target/employee-manager-1.0-SNAPSHOT.jar com.apptitude.employeemanager.App
```
