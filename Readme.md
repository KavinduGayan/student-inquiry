# Student Inquiry Service

Spring Boot service for managing students, lecturers, courses, and inquiries, with a REST API and OpenAPI/Swagger documentation.

## Tech stack

- Java 21
- Spring Boot (web, data JPA, validation, AOP)
- H2 in-memory database
- Springdoc OpenAPI UI

## Prerequisites

- Java 21 (per the Maven configuration)
- Maven (use the included Maven wrapper)

## Getting started

### Run the service

```bash
./mvnw spring-boot:run
```

The service starts on `http://localhost:8080`.

### API documentation

- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## Key API endpoints

Base path: `/api/v1`

- Students: `POST /students`, `GET /students`, `GET /students/{id}`
- Lecturers: `POST /lecturers`, `GET /lecturers`, `GET /lecturers/{id}`
- Courses: `POST /courses`, `GET /courses`, `GET /courses/{id}`
- Inquiries: `POST /inquiries`
- Lecturer inquiries: `GET /lecturers/{lecturerId}/inquiries`
- Inquiry responses: `POST /inquiry-items/{inquiryItemId}/responses`

## Database

The application uses an in-memory H2 database configured at startup. Data is not persisted between restarts.

## Tests

```bash
./mvnw test
```
