# GitHub Copilot Instructions for genCode

## Project Overview

genCode is a Java-based code generation tool that helps developers automatically generate code from database schemas. The project reduces repetitive coding work by generating stored procedures, CRUD operations, and front-end components.

**Key Features:**
- Generate Java code from stored procedures
- Generate stored procedures from database tables
- Generate auto-increment primary key SQL
- Generate database documentation (HTML, Excel, Word, Markdown)
- Generate code for front-end frameworks (Ant Design, ExtJS4)
- Support for in-memory mode (no database required via `gen.isdb=false`)

**Supported Databases:** Oracle, MySQL, MariaDB, SQL Server

## Technology Stack

- **Backend:** Spring Boot 2.7.18
- **Java Version:** Java 11 (current branch), Java 17 available in master branch
- **Build Tool:** Maven
- **Connection Pool:** Druid
- **Template Engine:** FreeMarker
- **View Layer:** Thymeleaf + ExtJS 4.0
- **Dependencies:**
  - Spring Boot Starter (JDBC, Web, Thymeleaf, FreeMarker)
  - Druid Spring Boot Starter
  - Multiple database drivers (MySQL, MariaDB, Oracle, SQL Server)

## Build and Test Commands

### Build
```bash
mvn clean package
# Skip tests during build
mvn -B package --file pom.xml '-Dmaven.test.skip=true'
```

### Test
```bash
mvn test
```

### Code Style Check
```bash
# Validate Spring Java Format (Java 17+ only)
mvn spring-javaformat:validate

# Apply Spring Java Format
mvn spring-javaformat:apply
```

### Run Application
```bash
# With database (default)
java -jar target/genCode-4.1.jar

# Without database (in-memory mode)
java -Dgen.isdb=false -jar target/genCode-4.1.jar
```

**Default Access:** http://localhost:8669/gen/  
**Druid Monitor:** Default credentials are `admin/admin`

## Code Style Guidelines

1. **Follow Spring Java Format:** This project uses Spring Java Format plugin. All code must pass `mvn spring-javaformat:validate`.

2. **Naming Conventions:**
   - Use standard Java naming conventions (camelCase for methods/variables, PascalCase for classes)
   - Service interfaces should be in `com.tlcsdm.gen.service` package
   - Service implementations should be in `com.tlcsdm.gen.service.impl` package
   - Controller classes should be in `com.tlcsdm.gen.controller` package

3. **Documentation:**
   - Add JavaDoc comments for public APIs and complex methods
   - Keep comments concise and meaningful
   - Update README.md if adding new features

4. **Architecture Patterns:**
   - Follow existing layered architecture: Controller → Service → Repository/DAO
   - Use factory pattern for creating database-specific implementations (see `factory` package)
   - Implement appropriate interfaces when adding new database types or code generation templates

## Key Architectural Components

### Service Layer
- **NameConventService:** Naming conventions/standards
- **DataBaseProcedureService:** Database-specific SQL and type mappings
- **DataBaseInfoService:** Data source management
- **DataBaseTableService:** Database table operations
- **DataBaseDocumentService:** Database documentation generation

### Abstract Classes & Templates
- **AbstractGenProcedureModel:** Template for stored procedure code generation
- **AbstractTableToProcedure:** Interface for table-to-procedure conversion
- **AbstractGenCodeModel:** Interface for code generation

### Caching
- **Cache:** Time-based expiring cache
- **CacheManage:** Cache management and maintenance
- **SimpleCache:** Simple cache for rarely-changing data
- **DataSourceUtilFactory:** Caches database connection pools

### Factory Pattern
- Classes in `factory` package maintain collections of interface implementations
- Used for managing different database types, code generation models, and naming conventions

### Scheduled Tasks
- Located in `schedule` package
- Weekly cleanup of database connection pool cache (every Friday)

## Adding New Features

### Adding a New Database Type
1. Implement `DataBaseProcedureService` interface
2. Implement `DataBaseTableService` interface
3. Implement `AbstractTableToProcedure` interface
4. Add new enum value to `DataBaseType`
5. Add the database driver dependency to `pom.xml`

### Adding a New Naming Convention
1. Create implementation class for `NameConventService`
2. Add new enum value to `NameConventType`

### Adding a New Code Generation Template
1. For stored procedure templates: Implement `AbstractGenProcedureModel`
2. For code templates: Implement `AbstractGenCodeModel`
3. Add corresponding enum value to `GenProcedureModelType` or `GenCodeModelType`

## Testing Guidelines

- Write tests in `src/test/java/com/tlcsdm/gen/`
- Follow existing test patterns (see `GenApplicationTests`, `TemplateTest`, etc.)
- Use JUnit for unit tests
- Tests should not require a live database connection when possible
- Test the in-memory mode (`gen.isdb=false`) for features that support it

## Database Configuration

- SQL files are located in `docs/database/`
- Configuration supports both database and in-memory modes via `gen.isdb` property
- In-memory mode stores data in memory (non-persistent) for development without database

## Important Constraints

1. **Minimal Changes:** Make the smallest possible changes to accomplish the task
2. **Preserve Compatibility:** Maintain backward compatibility with Java 11
3. **Don't Break Existing Features:** Ensure changes don't affect unrelated functionality
4. **Security:** Don't commit secrets, credentials, or sensitive data
5. **Template Files:** FreeMarker templates are in `src/main/resources/templates/`
6. **Static Resources:** Front-end resources are in `src/main/resources/static/`

## Related Projects

- Gradle version: https://github.com/UnknownInTheDream/genCode-gradle
- Java 17 version: Check master branch
- Java 1.8 version: Check genCode-3.3 branch

## Contact

For questions or feature requests, please create an issue or send email to project maintainers.
