# 🔧 Fixly - Work Order Management System

A comprehensive REST API for work order management designed to digitalize and transform maintenance processes in organizations with extensive physical infrastructure, industrial equipment, and maintenance services.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)

---

## 📋 Table of Contents

- [🎯 Project Overview](#-project-overview)
- [✨ Features](#-features)
- [🚀 Getting Started](#-getting-started)
- [⚙️ Installation](#️-installation)
- [🏃‍♂️ Running the Application](#️-running-the-application)
- [📚 API Documentation](#-api-documentation)
- [🌐 API Endpoints](#-api-endpoints)
- [🔧 Technologies](#-technologies)
- [📊 Architecture](#-architecture)
- [🧪 Tests](#-tests)
- [🔄 CI/CD Pipeline](#-cicd-pipeline)
- [🤝 Contributing](#-contributing)

---

## 🎯 Project Overview

In today's business landscape, **work order management** is a critical challenge for organizations with extensive physical infrastructure, industrial equipment, and maintenance services. Many companies still rely on **obsolete and fragmented methods** that result in lost requests in emails, disorganized spreadsheets without version control or traceability, critical information that gets lost or misinterpreted through verbal communication, and the use of **inadequate generic systems** that don't adapt to real operational needs.

This gap generates inefficiencies, errors, and hinders decision-making. **Fixly emerges as the comprehensive solution** to digitalize and transform these processes.

### 🎯 Specific Objectives

- **Digitalize Obsolete Processes**: Eliminate dependence on emails and spreadsheets
- **Centralize Communication**: Unify all documentation and communication
- **Guarantee Traceability**: Provide detailed tracking of work orders
- **Optimize Resources**: Efficiently manage technicians and priorities

---

## ✨ Features

- 👥 **Multi-Role User Management**: CLIENT, TECHNICIAN, SUPERVISOR roles with specific permissions
- 🔐 **Secure Authentication**: JWT-based authentication with BCrypt password encryption
- 📋 **Work Order Lifecycle**: Complete CRUD operations with state management
- 📊 **Status Tracking**: PENDING → ASSIGNED → IN_PROGRESS → READY_FOR_REVIEW → CLOSED
- 💬 **Comment System**: Internal and client notes with file attachments
- 📎 **File Management**: Cloudinary integration for document and image storage
- 🛡️ **Security**: Role-based access control and route protection
- 🚨 **Error Handling**: Comprehensive exception handling with custom responses
### Future implementations
- 🔍 **Advanced Filtering**: Search and pagination capabilities
- 📧 **Notifications**: Automatic email notifications
- 📈 **Reporting**: Metrics and productivity tracking

---

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 11+** - [Download here](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download here](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Download here](https://dev.mysql.com/downloads/)
- **Docker** (optional) - [Download here](https://www.docker.com/products/docker-desktop)
- **Git** - [Download here](https://git-scm.com/)

### Quick Start

```bash
# Clone the repository
git clone https://github.com/morenaperalta/fixly.git
cd fixly

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## ⚙️ Installation

### 1. Clone the Repository
```bash
git clone https://github.com/morenaperalta/fixly.git
cd fixly
```

### 2. Configure Database
Create a MySQL database and edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/fixly_db
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true

  # Cloudinary Configuration
  cloudinary:
    cloud-name: your_cloud_name
    api-key: your_api_key
    api-secret: your_api_secret

# JWT Configuration
jwt:
  secret: your_jwt_secret_key
  expiration: 86400000 # 24 hours

server:
  port: 8080

logging:
  level:
    com.morenaperalta.fixly: DEBUG
```

### 3. Configure Cloudinary
Sign up at [Cloudinary](https://cloudinary.com/) and get your credentials for file storage.

### 4. Build the Project
```bash
./mvnw clean install
```

---

## 🏃‍♂️ Running the Application

### Development Mode
```bash
./mvnw spring-boot:run
```

### Using Docker
```bash
# Build Docker image
docker build -t fixly-api .

# Run with Docker Compose
docker-compose up -d
```

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/
```

### Authentication
The API uses JWT tokens for authentication. Include the token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

### Swagger Documentation
Access interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

---

## 🌐 API Endpoints

### 🔐 Authentication

### 👥 User Management

### 📋 Work Orders

### 💬 Comments

### 📎 File Attachments

---

## 🔧 Technologies

### Backend Stack
- **Java 11+** - Core programming language
- **Spring Boot 2.7+** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **JWT** - Token-based authentication
- **MySQL** - Primary database
- **Hibernate** - ORM framework
- **Maven** - Dependency management
- **Lombok** - Boilerplate code reduction

### Cloud & Storage
- **Cloudinary** - File storage and management
- **Docker** - Containerization
- **GitHub Actions** - CI pipeline

### Development Tools
- **IntelliJ IDEA** - IDE
- **Swagger/OpenAPI** - API documentation
- **Postman** - API testing
- **Jira** - Project management
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework

---

## 📊 Architecture

### Database Design
The application uses the following main entities:
- **User**: User accounts with roles (CLIENT, TECHNICIAN, SUPERVISOR)
- **WorkOrder**: Main work order entity with lifecycle states
- **Comment**: Comments associated with work orders
- **Attachment**: File attachments for work orders and comments
- **WorkOrderAssignment**: Assignment tracking between supervisors and technicians

---

## 🧪 Tests

This project maintains a minimum **70% test coverage** as required. Tests include:

- **Unit Tests**: Service layer business logic
- **Integration Tests**: Controller endpoints
- **Security Tests**: Authentication and authorization
- **Repository Tests**: Data access layer

### Running Tests
```bash
# Run all tests
./mvnw test

# Run tests with coverage report
./mvnw test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

---

## 🔄 CI Pipeline

The project uses **GitHub Actions** for continuous integration and deployment:

---

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

### Development Workflow
1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Follow conventional commits**
   ```bash
   git commit -m "feat: add user authentication"
   git commit -m "fix: resolve work order status bug"
   git commit -m "docs: update API documentation"
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

### Code Standards
- Follow Java naming conventions
- Use DTOs for API requests/responses
- Implement proper exception handling
- Write comprehensive tests (70%+ coverage)
- Use meaningful commit messages (conventional commits)
- Add Javadoc for public methods

### Commit Convention
- `feat:` new features
- `fix:` bug fixes
- `docs:` documentation changes
- `style:` formatting changes
- `refactor:` code refactoring
- `test:` adding or updating tests
- `chore:` maintenance tasks

---

## Author

<table>
  <tr>
    <td>
      <a href="https://github.com/morenaperalta">
        <img src="https://github.com/morenaperalta.png" width="100px;" alt="Morena Peralta"/>
        <br />
        <sub><b>Morena Peralta</b></sub>
      </a>
      <br />
      <sub>Full Stack Developer</sub>
    </td>
  </tr>
</table>