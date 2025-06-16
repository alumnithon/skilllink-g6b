# SkillLink - Alumni Skill Matching Platform

SkillLink is a Spring Boot application designed to connect alumni and facilitate skill matching
within professional networks. This platform enables users to showcase their skills, find mentors,
and build meaningful professional connections.

## Technology Stack

This project is built with modern Java technologies and follows enterprise-grade development
practices:

### Core Technologies

- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 3.5.0** - Enterprise application framework
- **Spring Security** - JWT-based authentication and authorization
- **Spring Data JPA** - Data persistence with Hibernate
- **Spring Validation** - Bean validation for DTOs and API input validation
- **MySQL 8.0** - Relational database with UTF-8 support

### Architecture & Patterns

- **Hexagonal Architecture** - Clean separation of concerns
- **Domain-Driven Design** - Business logic organization
- **Repository Pattern** - Data access abstraction
- **Dependency Injection** - Constructor-based IoC

### Development Tools

- **Maven 3.6+** - Dependency management and build automation
- **Flyway** - Database migration versioning
- **SpringDoc OpenAPI 2.8.5** - API documentation and testing (Swagger UI)
- **Spring Boot Actuator** - Application monitoring and health checks
- **Lombok** - Boilerplate code reduction (getters/setters/constructors)
- **Spring Boot DevTools** - Development productivity and hot reloading

### Integration & Communication

- **SMTP Email** - Notification system with TLS support
- **CORS Configuration** - Frontend integration support
- **HikariCP** - High-performance database connection pooling (Spring Boot default)
- **Auth0 JWT 4.4.0** - JSON Web Token implementation for stateless authentication

### Testing & Documentation

- **Spring Boot Test** - Comprehensive testing framework
- **Spring Security Test** - Security testing utilities
- **Spring REST Docs** - API documentation generation from tests
- **AsciiDoctor Maven Plugin** - Documentation generation and publishing

## Project Structure

The application follows hexagonal architecture with domain-driven design principles:

```
src/main/java/alumnithon/skilllink/
├── SkilllinkApplication.java           # Main application entry point
├── controller/                         # Global controllers (Auth, etc.)
│   └── AuthController.java
├── domain/                            # Business domains
│   ├── auth/                          # Authentication domain
│   └── userprofile/                   # User profile domain
│       ├── controller/                # REST endpoints (adapters)
│       ├── dto/                       # Data Transfer Objects
│       ├── interface/                 # Domain contracts/interfaces
│       ├── model/                     # Domain entities
│       ├── repository/                # Data access implementations
│       └── service/                   # Business logic and use cases
├── infrastructure/                    # External concerns
│   ├── config/                        # Spring configuration
│   │   ├── SecurityConfig.java        # JWT security setup
│   │   ├── SecurityFilter.java        # Authentication filter
│   │   ├── SwaggerConfig.java         # API documentation
│   │   └── WebConfig.java             # Web layer configuration
│   └── email/                         # Email service infrastructure
└── shared/                           # Cross-cutting concerns
    └── exception/                     # Global exception handling

src/main/resources/
├── application.properties             # Application configuration
├── db/migration/                      # Flyway database migrations
├── static/                           # Static web resources
└── templates/                        # Email/view templates
```

### Domain Organization

Each business domain (like `userprofile`) follows a consistent structure:

- **controller/** - REST API endpoints (hexagonal adapters)
- **dto/** - Data transfer objects for API communication
- **interface/** - Domain service contracts and repository interfaces
- **model/** - Domain entities and business objects
- **repository/** - Data access layer implementations
- **service/** - Business logic and use case orchestration

This structure ensures clear separation between:

- **Domain Layer** - Pure business logic and entities
- **Application Layer** - Use cases and domain service orchestration
- **Infrastructure Layer** - External integrations (database, web, email)
- **Adapters** - Interface implementations connecting external world to domain

## Prerequisites

Before running the application, ensure you have the following installed and configured:

### Required Software

- **Java 21+** - OpenJDK or Oracle JDK
- **Maven 3.6+** - For dependency management and building
- **MySQL 8.0** - Database server
- **Git** - Version control

### Database Setup

1. **Install MySQL 8.0**

   ```bash
   # On macOS with Homebrew
   brew install mysql@8.0
   brew services start mysql@8.0

   # On Ubuntu/Debian
   sudo apt update
   sudo apt install mysql-server-8.0
   sudo systemctl start mysql
   ```

2. **Create Database Schema**

   ```sql
   CREATE DATABASE skilllink_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'root'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON skilllink_db.* TO 'root'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Database Configuration** The application expects the following default database configuration:
   - **Host**: localhost
   - **Port**: 3306
   - **Database**: skilllink_db
   - **Username**: root (or configure via environment variables)
   - **Password**: password (or configure via environment variables)

## Environment Variables

Configure the following environment variables for production deployment:

### Database Configuration

```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=skilllink_db
export DB_USER=root
export DB_PASSWORD=password
```

### Security Configuration

```bash
export JWT_SECRET=your_secure_jwt_secret_key_here
```

### Email Configuration

```bash
export EMAIL_HOST=smtp.your-provider.com
export EMAIL_PORT=587
export EMAIL_USER=your_email@domain.com
export EMAIL_PASS=your_email_password
```

### Frontend Configuration

```bash
export FRONTEND_CORS_URL=http://localhost:5173
export FRONTEND_REDIRECT_URL=http://localhost:5173
```

## Installation & Setup

1. **Clone the Repository**

   ```bash
   git clone <repository-url>
   cd skilllink-g6b
   ```

2. **Configure Application Properties**

   - Copy `src/main/resources/application.properties` and customize if needed
   - Or set environment variables as shown above

3. **Install Dependencies**

   ```bash
   mvn clean install
   ```

4. **Database Migration** The application uses Flyway for database migrations. Migrations are
   automatically applied on startup from `src/main/resources/db/migrations/`.

5. **Run the Application**

   ```bash
   # Development mode
   mvn spring-boot:run

   # Or run the JAR file
   mvn clean package
   java -jar target/skilllink-*.jar
   ```

## Application Endpoints

- **API Documentation**: http://localhost:8080/api/docs/index.html
- **Health Check**: http://localhost:8080/actuator/health
- **API Docs JSON**: http://localhost:8080/docs

## Features

- **User Authentication & Authorization** - JWT-based security
- **Skill Matching** - Connect users based on complementary skills
- **Profile Management** - Comprehensive user profiles
- **Email Notifications** - SMTP integration for communications
- **Database Migrations** - Flyway for schema versioning
- **API Documentation** - Swagger/OpenAPI integration
- **Health Monitoring** - Spring Boot Actuator endpoints

## Development

### Database Schema Management

- Database migrations are located in `src/main/resources/db/migrations/`
- Flyway automatically applies pending migrations on application startup
- Use the naming convention: `V{version}__{description}.sql`

### Testing

```bash
# Run all tests
mvn test

# Run with specific profile
mvn test -Dspring.profiles.active=test
```

### Building for Production

```bash
# Create production JAR
mvn clean package -Pprod

# Build Docker image (if Dockerfile exists)
docker build -t skilllink:latest .
```

## Configuration Details

### Security

- JWT tokens are used for authentication
- CORS is configured for frontend integration
- Error stack traces are hidden in production

### Database

- Uses MySQL 8.0 with UTF-8 character set
- Connection pooling via HikariCP
- SQL logging enabled for development

### Email

- SMTP configuration with TLS support
- Configurable timeout settings
- Health checks disabled by default

## Troubleshooting

### Common Issues

1. **Database Connection Issues**

   - Verify MySQL is running: `brew services list | grep mysql`
   - Check database credentials and permissions
   - Ensure database `skilllink_db` exists

2. **Port Conflicts**

   - Default port is 8080, change via `server.port` property
   - Check if port is in use: `lsof -i :8080`

3. **Migration Failures**
   - Check migration file syntax in `db/migrations/`
   - Verify database user has DDL permissions
   - Review Flyway logs for specific errors

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Commit changes: `git commit -am 'Add new feature'`
4. Push to branch: `git push origin feature/new-feature`
5. Submit a pull request

## Support

For issues and questions, please:

1. Check the troubleshooting section above
2. Review application logs
3. Create an issue in the repository with detailed information

---

**Note**: This application is part of the Alumnithon project and is designed for educational and
networking purposes within alumni communities.
