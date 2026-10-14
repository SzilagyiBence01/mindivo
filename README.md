# Mindivo

Mindivo is an educational platform for creating and studying structured learning materials.

The application allows users to create content blocks containing different types of learning content, such as text, images and quizzes. Users can also like and save content blocks for later use.

## Current status

🚧 The backend is currently implemented and tested. The frontend is planned.

Implemented:
- User registration and login
- JWT-based authentication
- User account management
- Content block creation, retrieval, updating and deletion
- Nested content management
- Content ordering
- Like and unlike functionality
- Save and unsave functionality
- Retrieving user's own content blocks
- Retrieving saved content blocks
- Service-layer unit tests with JUnit and Mockito
- API testing with Postman
- GitHub Actions CI with Maven

In progress:
- Frontend application

Planned:
- Frontend implementation
- Additional content types
- Additional tests and validation
- Deployment of the application

## Technologies

- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Maven
- Lombok
- JUnit
- Mockito
- Postman
- GitHub Actions

## Architecture

The backend follows a layered architecture:

- **Controller** – handles HTTP requests and responses
- **Service** – contains business logic
- **Repository** – handles database access
- **Entity** – represents database entities
- **DTO** – represents API request and response data
- **Mapper** – converts between entities and DTOs
