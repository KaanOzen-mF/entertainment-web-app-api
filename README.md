Entertainment Web App - Backend API
===================================

This repository contains the backend API for the Full-Stack Entertainment Web App. It is a Java/Spring Boot application responsible for user authentication (registration and login) and managing user bookmarks.

➡️ The corresponding frontend repository can be found here:  <https://github.com/KaanOzen-mF/entertainment-web-app>

Table of Contents
-----------------

-   [Overview](https://www.google.com/search?q=%23overview)

-   [API Endpoints](https://www.google.com/search?q=%23api-endpoints)

-   [Tech Stack](https://www.google.com/search?q=%23tech-stack)

-   [Getting Started](https://www.google.com/search?q=%23getting-started)

-   [Prerequisites](https://www.google.com/search?q=%23prerequisites)

-   [Installation](https://www.google.com/search?q=%23installation)

-   [Configuration](https://www.google.com/search?q=%23configuration)

-   [Security](https://www.google.com/search?q=%23security)

Overview
--------

This API serves as the persistence and authentication layer for the frontend application. It provides a secure way for users to create accounts, log in, and save their favorite movies and TV shows. It uses a PostgreSQL database to store user data and bookmarks, and JWT for stateless authentication.

API Endpoints
-------------

All endpoints are prefixed with /api/v1.

### Authentication (/auth)

|

Method

|

Endpoint

|

Description

|

Access

|
|

POST

|

/auth/register

|

Registers a new user.

|

Public

|
|

POST

|

/auth/login

|

Authenticates a user and returns a JWT.

|

Public

|

### Bookmarks (/bookmarks)

These endpoints require a valid JWT in the Authorization: Bearer <token> header.

|

Method

|

Endpoint

|

Description

|

Access

|
|

GET

|

/bookmarks

|

Gets all bookmarked TMDB IDs for the current user.

|

Protected

|
|

POST

|

/bookmarks/{tmdbId}

|

Adds a media item to the user's bookmarks.

|

Protected

|
|

DELETE

|

/bookmarks/{tmdbId}

|

Removes a media item from the user's bookmarks.

|

Protected

|

Tech Stack
----------

Getting Started
---------------

To get the backend server running locally, follow these steps.

### Prerequisites

-   Java 21+

-   Maven

-   PostgreSQL

### Installation

1.  Clone the repository:\
    git clone [https://github.com/KaanOzen-mF/entertainment-web-app-api.git](https://github.com/KaanOzen-mF/entertainment-web-app-api.git)

2.  Navigate to the project directory:\
    cd entertainment-web-app-api

3.  Create and Configure application.properties:

-   Navigate to the src/main/resources/ directory.

-   Create a file named application.properties.

-   Add your database and JWT secret key configurations to this file. See the [Configuration](https://www.google.com/search?q=%23configuration) section below for the required properties.

1.  Build and Run the Application:

-   Build the project using Maven:\
    mvn clean install

-   Run the application from your favorite IDE (e.g., IntelliJ IDEA, Eclipse) or via the command line:\
    mvn spring-boot:run

The server will start on http://localhost:8080.

Configuration
-------------

You must create an application.properties file in src/main/resources/. This file is excluded from Git via .gitignore to protect sensitive information.

Example application.properties:

# PostgreSQL Database Connection\
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name\
spring.datasource.username=your_postgres_user\
spring.datasource.password=your_postgres_password

# JPA and Hibernate Settings\
spring.jpa.hibernate.ddl-auto=update\
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Secret Key (must be a long, secure, Base64-encoded string)\
jwt.secret.key=b3VyLXNlY3JldC1rZXktZm9yLWp3dC1zaWduYXR1cmUtbmVlZHMtdG8tYmUtbG9uZy1hbmQtc2VjdXJlCg==

# Logging Levels for Debugging (Optional)\
logging.level.org.springframework.web=DEBUG\
spring.jpa.show-sql=true\
spring.jpa.properties.hibernate.format_sql=true

Security
--------

-   Password Hashing: User passwords are not stored in plain text. They are securely hashed using BCrypt via BCryptPasswordEncoder.

-   Authentication: The API uses stateless JWT-based authentication. The login endpoint provides a token that must be included in the Authorization header for all protected endpoints.

-   CORS: A global CORS configuration is in place to only allow requests from the specified frontend origin (http://localhost:3000).