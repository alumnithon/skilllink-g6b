## Running with Docker

This project provides a ready-to-use Docker setup for local development and deployment. The included `Dockerfile` and `docker-compose.yml` files ensure consistent builds and easy startup of all required services.

### Requirements

- **Docker** and **Docker Compose** installed on your system
- No additional local installations of Java, Maven, or MySQL are required when using Docker

### Services and Ports

- **java-app** (Spring Boot application)
  - Exposes: `8080` (mapped to host)
- **mysql-db** (MySQL 8.0 database)
  - Exposes: `3306` (mapped to host)

### Environment Variables

The application requires several environment variables for configuration. These can be set in a `.env` file at the project root or directly in the `docker-compose.yml` file. Key variables include:

- `DB_HOST` (default: `mysql-db`)
- `DB_PORT` (default: `3306`)
- `DB_NAME` (default: `skilllink_db`)
- `DB_USER` (default: `root`)
- `DB_PASSWORD` (should be set securely)
- `JWT_SECRET`, `EMAIL_HOST`, `EMAIL_PORT`, `EMAIL_USER`, `EMAIL_PASS` (for security and email integration)

> **Note:** The `DB_PASSWORD` and other sensitive values should be set via a `.env` file or Docker secrets. Do not hardcode them in version control.

### Build and Run Instructions

1. **(Optional) Create a `.env` file**
   
   Copy `.env.example` to `.env` and update values as needed:
   
   ```bash
   cp .env.example .env
   # Edit .env to set DB_PASSWORD, JWT_SECRET, etc.
   ```

2. **Build and start the services**

   From the project root, run:

   ```bash
   docker compose up --build
   ```

   This will:
   - Build the Java application using the multi-stage Dockerfile (Java 21, Maven)
   - Start the Spring Boot app and MySQL 8.0 database
   - Apply database migrations automatically on startup

3. **Access the application**

   - API: [http://localhost:8080](http://localhost:8080)
   - API Docs: [http://localhost:8080/api/docs/index.html](http://localhost:8080/api/docs/index.html)
   - MySQL: `localhost:3306` (use credentials from your `.env` file)

### Special Configuration

- The application runs as a non-root user inside the container for improved security.
- MySQL data is persisted in a Docker volume (`mysql-data`) to avoid data loss between restarts.
- The `docker-compose.yml` file is pre-configured for local development, but you should review and adjust environment variables for production deployments.

---

For more details on environment variables and configuration, see the [Environment Variables](#environment-variables) section above.