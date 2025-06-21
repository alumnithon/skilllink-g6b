# SkillLink - Alumni Skill Matching Platform

[![Development PR Checks](https://github.com/alumnithon/skilllink-g6b/actions/workflows/dev_pr.yml/badge.svg)](https://github.com/alumnithon/skilllink-g6b/actions/workflows/dev_pr.yml)[![.github/workflows/maven.yml](https://github.com/alumnithon/skilllink-g6b/actions/workflows/maven.yml/badge.svg)](https://github.com/alumnithon/skilllink-g6b/actions/workflows/maven.yml)

SkillLink is a Spring Boot application designed to connect alumni and facilitate skill matching
within professional networks. This platform enables users to showcase their skills, find mentors,
and build meaningful professional connections.

## Technology Stack

- **Java 21** - Latest LTS with modern language features
- **Spring Boot 3.5.0** - Enterprise application framework with Spring Security (JWT), Spring Data
  JPA, Validation
- **MySQL 8.0** - Relational database with UTF-8 support
- **Maven 3.5+** - Dependency management and build automation
- **Docker & Docker Compose** - Containerized deployment
- **Flyway** - Database migration versioning
- **SpringDoc OpenAPI 2.8.5** - API documentation (Swagger UI)

### Architecture

Follows **Hexagonal Architecture** with **Domain-Driven Design**:

- **Domain Layer** - Pure business logic and entities
- **Application Layer** - Use cases and domain service orchestration
- **Infrastructure Layer** - External integrations (database, web, email)
- **Adapters** - Interface implementations connecting external world to domain

## Quick Start

### Option 1: Docker (Recommended)

**Requirements:** Docker and Docker Compose only

```bash
# 1. (Optional) Configure environment
cp .env.example .env  # Edit .env for DB_PASSWORD, JWT_SECRET, etc.

# 2. Build and start services
docker compose up --build -d

# 3. Access application
# API: http://localhost:8080/api/
# Swagger: http://localhost:8080/api/docs/swagger-ui/index.html
```

**Services:**

- **java-app** (Spring Boot): Port `8080`
- **mysql-db** (MySQL 8.0): Port `3306`

### Option 2: Local Development

**Requirements:** Java 21+, Maven 3.6+, MySQL 8.0

```bash
# 1. Setup database
mysql -u root -p
CREATE DATABASE skilllink_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 2. Set environment variables
export DB_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret

# 3. Run application
mvn spring-boot:run
```

## Environment Variables

### Required

```bash
DB_PASSWORD=your_secure_password
JWT_SECRET=your_secure_jwt_secret_key
```

### Optional (with defaults)

```bash
DB_HOST=localhost          # mysql-db for Docker
DB_PORT=3306
DB_NAME=skilllink_db
DB_USER=root
EMAIL_HOST=smtp.provider.com
EMAIL_PORT=587
EMAIL_USER=your_email
EMAIL_PASS=your_email_password
FRONTEND_CORS_URL=http://localhost:5173
```

## Project Structure

```
src/main/java/alumnithon/skilllink/
├── SkilllinkApplication.java           # Main entry point
├── controller/                         # Global controllers (Auth)
├── domain/                            # Business domains
│   ├── auth/                          # Authentication domain
│   └── userprofile/                   # User profile domain
│       ├── controller/                # REST endpoints
│       ├── dto/                       # Data Transfer Objects
│       ├── interface/                 # Domain contracts
│       ├── model/                     # Domain entities
│       ├── repository/                # Data access layer
│       └── service/                   # Business logic
├── infrastructure/                    # External concerns
│   ├── config/                        # Security, Swagger, Web config
│   └── email/                         # Email service
└── shared/exception/                  # Global exception handling
```

## API Endpoints

- **API Documentation:** http://localhost:8080/api/docs/index.html
- **Health Check:** http://localhost:8080/actuator/health
- **API Docs JSON:** http://localhost:8080/docs

## Development

```bash
# Run tests
mvn test

# Build for production
mvn clean package -Pprod

# Database migrations (auto-applied on startup)
# Located in: src/main/resources/db/migrations/
```

## Features

- User Authentication & Authorization (JWT-based)
- Skill Matching - Connect users based on complementary skills
- Profile Management - Comprehensive user profiles
- Email Notifications - SMTP integration
- Database Migrations - Flyway schema versioning
- API Documentation - Swagger/OpenAPI integration
- Health Monitoring - Spring Boot Actuator

## Troubleshooting

**Database Issues:**

- Verify MySQL is running: `brew services list | grep mysql` (macOS)
- Check database credentials and ensure `skilllink_db` exists

**Port Conflicts:**

- Default port is 8080, change via `server.port` property
- Check port usage: `lsof -i :8080`

**Docker Issues:**

- Ensure Docker services are running: `docker compose ps`
- Check logs: `docker compose logs java-app`

## Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature/new-feature`
3. Commit changes: `git commit -am 'Add new feature'`
4. Push to branch: `git push origin feature/new-feature`
5. Submit a pull request
