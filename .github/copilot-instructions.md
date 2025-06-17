# Copilot Instructions for SkillLink Project

## Project Overview

This is a Spring Boot 3.5.0 application using Java 21 with hexagonal architecture pattern. The
project follows domain-driven design principles with clear separation between domain logic,
infrastructure concerns, and application services.

## Architecture and Code Organization

### Follow Existing Package Structure

When creating new features/domains, replicate the existing `userprofile` package structure:

```
domain/
  {feature-name}/
    controller/     # REST controllers (adapters)
    dto/           # Data Transfer Objects
    interface/     # Domain interfaces/contracts
    model/         # Domain entities
    repository/    # Repository interfaces and implementations
    service/       # Domain services and use cases
```

### Hexagonal Architecture Principles

- **Domain Layer**: Contains business logic, entities, and domain services
- **Application Layer**: Use cases and application services that orchestrate domain operations
- **Infrastructure Layer**: External concerns (database, web, security configuration)
- **Adapters**: Controllers and repositories that connect external world to domain

## Code Style and Standards

### Follow Spring Framework Conventions

- Use Spring Boot conventions and naming patterns
- Follow standard Spring package organization
- Utilize Spring's dependency injection best practices

### Documentation Requirements

- Document only public APIs and complex business logic
- Include Swagger/OpenAPI annotations for all REST endpoints
- Add brief comments for non-obvious business rules
- No need for Javadoc on simple getters/setters

## Dependency Injection

- **Always use constructor injection** (Spring best practice)
- Declare dependencies as `private final` fields
- Use constructor for required dependencies only
- Example:

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

## Security Implementation

### JWT Authentication

- **Always use JWT tokens, never session-based authentication**
- Include basic security comments for authentication/authorization logic
- Maintain stateless security configuration
- Follow existing `SecurityConfig` and `SecurityFilter` patterns

### Example Security Annotations

```java
@PreAuthorize("hasRole('USER')")
@GetMapping("/profile")
public ResponseEntity<UserProfileDto> getProfile() {
    // implementation
}
```

## API Design and Documentation

### REST API Standards

- **Generate comprehensive OpenAPI/Swagger documentation** for all endpoints
- Use proper HTTP methods and status codes
- Follow RESTful naming conventions
- Include detailed request/response examples in Swagger

### Controller Patterns

```java
@RestController
@RequestMapping("/api/{domain}")
@Tag(name = "Domain Management", description = "Operations related to domain entities")
public class DomainController {
    // Always include Swagger annotations
    @Operation(summary = "Get entity by ID", description = "Retrieves a specific entity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entity found"),
        @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityDto> getById(@PathVariable Long id) {
        // implementation
    }
}
```

## Database and Repository Pattern

### Repository Implementation

- Use the existing repository pattern with interfaces and implementations
- Prefer custom query methods over native SQL
- Implement repository interfaces in the `repository` package
- Use JPA conventions for method naming

### Example Repository Structure

```java
// In interface/ package
public interface UserRepository {
    Optional<User> findById(Long id);
    List<User> findByActiveTrue();
}

// In repository/ package
@Repository
public class UserRepositoryImpl implements UserRepository {
    // JPA implementation
}
```

## Exception Handling

- Use minimal custom exceptions, rely on Spring framework defaults
- Implement global exception handling with `@ControllerAdvice`
- Return appropriate HTTP status codes
- Keep error responses consistent across the application

## Testing Strategy

### Unit Testing Focus

- **Write unit tests for all service methods and controllers**
- **Use Test-Driven Development (TDD) approach when possible**
- Mock external dependencies using Mockito
- Test business logic thoroughly
- Focus on edge cases and error scenarios

### Testing Patterns

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserWhenValidIdProvided() {
        // Given - When - Then pattern
    }
}
```

## Logging Strategy

- **Minimal logging approach**: errors and important business events only
- Use SLF4J with Logback (Spring Boot default)
- Log authentication/authorization events
- Log critical business operations (user creation, important updates)
- Avoid debug logging in production code

### Logging Examples

```java
private static final Logger logger = LoggerFactory.getLogger(UserService.class);

// Log important business events
logger.info("User created with ID: {}", user.getId());

// Log errors with context
logger.error("Failed to create user with email: {}", email, exception);
```

## DTO and Model Patterns

### Data Transfer Objects

- Create DTOs in the `dto/` package for API communication
- Use record classes for immutable DTOs when appropriate
- Include validation annotations on DTOs
- Keep DTOs focused on specific use cases

### Domain Models

- Place entity classes in `model/` package
- Use JPA annotations appropriately
- Keep domain logic within entities when suitable
- Separate persistence concerns from business logic

## Common Code Generation Patterns

### New Feature Implementation

When implementing a new domain feature:

1. **Create package structure** following `userprofile` pattern
2. **Start with domain model** (entity in `model/` package)
3. **Define repository interface** in `interface/` package
4. **Implement repository** in `repository/` package
5. **Create service** with business logic in `service/` package
6. **Build DTOs** for API communication in `dto/` package
7. **Implement controller** with full Swagger documentation in `controller/` package
8. **Write comprehensive unit tests** for service and controller layers

### Always Include

- Swagger/OpenAPI annotations on all endpoints
- Constructor injection for dependencies
- Proper HTTP status codes and error handling
- Unit tests with TDD approach
- Minimal but meaningful logging
- JWT-based security considerations

## Technology Stack Reminders

- **Java 21** - use modern Java features when appropriate
- **Spring Boot 3.5.0** - follow latest Spring conventions
- **JPA/Hibernate** - for data persistence
- **JWT** - for stateless authentication
- **Swagger/OpenAPI** - for API documentation
- **Maven** - for dependency management

## Example Implementation Template

```java
// Controller example with full annotations
@RestController
@RequestMapping("/api/examples")
@Tag(name = "Example Management")
public class ExampleController {
    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @Operation(summary = "Create new example")
    @PostMapping
    public ResponseEntity<ExampleDto> create(@Valid @RequestBody CreateExampleDto dto) {
        ExampleDto created = exampleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
```

Remember: **Follow existing patterns**, **prioritize TDD**, **document APIs thoroughly**, and
**maintain the hexagonal architecture structure**.
