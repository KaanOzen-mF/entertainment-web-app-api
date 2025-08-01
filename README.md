# Entertainment Web App - Backend API

This repository contains the backend API for the Full-Stack Entertainment Web App. It is a Java/Spring Boot application responsible for user authentication (registration and login) and managing user bookmarks.

➡️ **The corresponding frontend repository can be found here:** [https://github.com/KaanOzen-mF/entertainment-web-app](https://github.com/KaanOzen-mF/entertainment-web-app)

## Table of Contents

- [Overview](#overview)
- [API Endpoints](#api-endpoints)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Configuration](#configuration)
- [Security](#security)

## Overview

This API serves as the persistence and authentication layer for the frontend application. It provides a secure way for users to create accounts, log in, and save their favorite movies and TV shows. It uses a PostgreSQL database to store user data and bookmarks, and JWT for stateless authentication.

## API Endpoints

All endpoints are prefixed with `/api/v1`.

### Authentication (`/auth`)

| Method | Endpoint          | Description                  | Access |
| :----- | :---------------- | :--------------------------- | :----- |
| `POST` | `/auth/register`  | Registers a new user.        | Public |
| `POST` | `/auth/login`     | Authenticates a user and returns a JWT. | Public |

### Bookmarks (`/bookmarks`)

*These endpoints require a valid JWT in the `Authorization: Bearer <token>` header.*

| Method   | Endpoint             | Description                                  | Access    |
| :------- | :------------------- | :------------------------------------------- | :-------- |
| `GET`    | `/bookmarks`         | Gets all bookmarked TMDB IDs for the current user. | Protected |
| `POST`   | `/bookmarks/{tmdbId}` | Adds a media item to the user's bookmarks.   | Protected |
| `DELETE` | `/bookmarks/{tmdbId}` | Removes a media item from the user's bookmarks. | Protected |

## Tech Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)


## Getting Started

To get the backend server running locally, follow these steps.

### Prerequisites

- Java 21+
- Maven
- PostgreSQL

### Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/KaanOzen-mF/entertainment-web-app-api.git](https://github.com/KaanOzen-mF/entertainment-web-app-api.git)
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd entertainment-web-app-api
    ```
3.  **Create and Configure `application.properties`:**
    -   Navigate to the `src/main/resources/` directory.
    -   Create a file named `application.properties`.
    -   Add your database and JWT secret key configurations to this file. See the [Configuration](#configuration) section below for the required properties.
4.  **Build and Run the Application:**
    -   Build the project using Maven:
        ```bash
        mvn clean install
        ```
    -   Run the application from your favorite IDE (e.g., IntelliJ IDEA, Eclipse) or via the command line:
        ```bash
        mvn spring-boot:run
        ```
    The server will start on `http://localhost:8080`.

## Configuration

You must create an `application.properties` file in `src/main/resources/`. This file is excluded from Git via `.gitignore` to protect sensitive information.

**Example `application.properties`:**
```properties
# PostgreSQL Database Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=your_postgres_user
spring.datasource.password=your_postgres_password

# JPA and Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Secret Key (must be a long, secure, Base64-encoded string)
jwt.secret.key=JWT_SECRET_KEY_BASE64

# Logging Levels for Debugging (Optional)
logging.level.org.springframework.web=DEBUG
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
# Security
- Password Hashing: User passwords are not stored in plain text. They are securely hashed using BCrypt via BCryptPasswordEncoder.
- Authentication: The API uses stateless JWT-based authentication. The login endpoint provides a token that must be included in the Authorization header for all protected endpoints.
- CORS: A global CORS configuration is in place to only allow requests from the specified frontend origin (http://localhost:3000).