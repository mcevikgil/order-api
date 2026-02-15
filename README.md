# order-api

Spring Boot REST API with JWT authentication

## Tech Stack
- Java 17
- Spring Boot 3.3.0
- PostgreSQL
- JWT Authentication
- Maven

## Features
- User registration and login
- JWT token-based authentication
- BCrypt password encryption
- RESTful API endpoints
- Swagger/OpenAPI documentation

## API Endpoints
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/users` - List users
- `GET /api/users/{id}` - Get user by ID

## Setup
1. Configure PostgreSQL in `application.yml`
2. Run: `mvn spring-boot:run`
3. API: http://localhost:8080
4. Swagger: http://localhost:8080/swagger-ui.html
