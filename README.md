# task-manager-api

## Overview

Task Manager is a small REST API built with Spring Boot for creating, updating and tracking tasks. This README explains how to build, run and test the project locally and provides a short orientation to the repository layout and contribution guidelines.

> Note: the actual application source lives in the `taskmanager` subdirectory of this workspace — `cd taskmanager` before running build commands.

## Tech stack

- Java 17
- Spring Boot
- Maven (project includes the Maven Wrapper)
- Lombok (compile-time)

## Prerequisites

- JDK 17 installed (or use a toolchain that provides Java 17)
- Git (optional, for cloning)
- On Windows use the included Maven wrapper (`mvnw.cmd`) to avoid a global Maven install

## Quick start (Windows / cmd.exe)

1. Open a command prompt and change to the project directory:

   ```
   cd taskmanager
   ```

2. Run the application using the included Maven wrapper:

   ```
   .\mvnw.cmd spring-boot:run
   ```

3. The API will start on the default Spring Boot port (8080) unless configured otherwise in `src/main/resources/application.properties`.

Alternative: build a runnable jar and run it directly

```
cd taskmanager
.\mvnw.cmd -DskipTests package
java -jar target\taskmanager-0.0.1-SNAPSHOT.jar
```

If you have Maven installed globally, you can replace `.\mvnw.cmd` with `mvn`.

## Running tests

```
cd taskmanager
.\mvnw.cmd test
```

## Configuration

Application configuration lives under `src/main/resources/application.properties`. Add environment-specific overrides or use command-line properties when starting the jar, for example:

```
java -jar target\taskmanager-0.0.1-SNAPSHOT.jar --server.port=8081
```

## Project structure

- `src/main/java` - application sources
- `src/main/resources` - application configuration, static files, and templates
- `src/test/java` - unit and integration tests

The main Spring Boot application class is `com.example.taskmanager.TaskmanagerApplication`.

## Swagger UI

Swagger UI is accessible in `http://localhost:8080/swagger-ui.html`.
Can be used to visualize endpoints documentation, send requests for test and inspect models / responses.

## Troubleshooting

- If you see Lombok-related compilation errors in your IDE, install the Lombok plugin and enable annotation processing.
- If the port is already in use, change `server.port` in `application.properties` or pass `--server.port=XXXX` at startup.
