# Antharos BFF

## Overview

This Backend for Frontend (BFF) service powers the Antharos HR platform using Spring Boot with a Command Query Responsibility Segregation (CQRS) architecture. It serves as the API layer between the React frontend and the core business services, optimizing communication and enhancing the user experience.

## Technology Stack

- **Framework**: Spring Boot 3.4
- **Build Tool**: Maven
- **Language**: Java 21
- **Architecture Pattern**: CQRS (Command Query Responsibility Segregation)
- **Authentication**: JWT with Spring Security
- **API Documentation**: Swagger/OpenAPI 3

## Architecture

The BFF follows CQRS pattern with separate models for:

### Commands
Commands represent operations that change state (create, update, delete).

### Queries
Queries represent operations that read state but do not modify it.

## Role-Based Access Control

The BFF enforces authorization through Spring Security with the following roles:

- `EMPLOYEE`: Basic staff members
- `DEPARTMENT_HEAD`: Managers of specific departments
- `COMPANY_MANAGEMENT`: Executive-level management

## Getting Started

### Prerequisites

- JDK 21+
- Maven 3.9+

### Installation

```bash
# Clone the repository
git clone https://github.com/miguelgardepuoc/mic-bff.git
cd antharos-bff

# Build the project
./mvnw clean install
```

### Running Locally

```bash
# Run with default profile
./mvnw spring-boot:run

# Run with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The API will be available at `http://localhost:8080/bff`.
Swagger documentation will be available at `http://localhost:8080/swagger-ui.html`.
