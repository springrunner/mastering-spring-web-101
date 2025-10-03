# Claude Code Config for Mastering Spring Web 101

This document provides essential context for AI agents (like Claude Code) working on this project.

Guidelines for maintaining this document:
- Focus on "where things are" and "how to approach them", not detailed implementation
- Avoid listing specific APIs, fields, or implementation details - point to reference code instead
- Use major version numbers only (e.g., Spring Boot 3.x, not 3.3.2)
- Separate client and server concerns clearly - they use different technologies and architectures
- Keep it concise - AI should read actual code for specifics, use this for structure and principles

## Overview

Todoapp web application built with Spring Boot (server) and Vanilla JavaScript (client) for learning Spring MVC.

Repository: https://github.com/springrunner/mastering-spring-web-101

## Project structure

- client: Vanilla JS frontend with Thymeleaf templates
- server: Spring Boot application
- docs: Application specifications and textbook

## Build & Run Commands

Server:
- Build: `cd server && ./gradlew build`
- Run: `cd server && ./gradlew bootRun`
- Test: `cd server && ./gradlew test`

Client:
- Install: `cd client && npm install`
- Dev: `cd client && npm run dev`
- Build: `cd client && npm run build`

Docker:
- Run all: `docker compose up --build -d`

## Git Commit Convention

Follow Conventional Commits format with scope:
- Format: `<type>(<scope>): <description>`
- Types: feat, fix, docs, refactor, test, chore, style, perf
- Scope: server, client (omit for project-wide changes)
- Examples:
  - `feat(server): Implement request mapping for login endpoint`
  - `feat(client): Implement user profile for Web API`
  - `refactor(server): Reorganize application architecture`
  - `chore(server): Upgrade Gradle version`
  - `docs: Update README`

---

## Server (Spring Boot)

### Technology stacks

- Language: Java 21
- Framework: Spring Boot 3.x, Spring MVC, Spring Data JPA
- Build tools: Gradle 8.x
- Database: H2 Database
- Template engine: Thymeleaf

### Project structure

Based on Clean Architecture (Core ← Adapters):
- server/src/main/java/todoapp/core: Domain models and application use cases (framework-independent)
- server/src/main/java/todoapp/web: Web controllers and MVC configuration
- server/src/main/java/todoapp/data: Repository implementations
- server/src/main/java/todoapp/security: Authentication and authorization
- server/src/main/resources/application.yaml: Spring Boot configuration

### Architecture principles

- Dependency rule: Adapters depend on Core, never the reverse
- core/ is independent of Spring, JPA, and external frameworks
- Use @Profile to switch between InMemory (development) and JPA (production) implementations
- Domain structure: core/{domain}/domain/ (models, interfaces), core/{domain}/application/ (use cases)
- Check existing domains (todo, user) for patterns when adding new features

### Key patterns

Use Case pattern:
- One interface per business operation
- Name should be the goal as a short active verb phrase (AddTodo, FindTodos, ModifyTodo, RemoveTodo)
- Implementation class: Default*Manager with @Service @Transactional

Repository pattern:
- Interface in core/{domain}/domain/
- Implementations in data/ with @Profile and @Repository

Controller pattern:
- MVC: Return HTML views, handle form submissions
- REST: @RestController with record-based DTOs, return JSON
- See TodoRestController, LoginController for reference

Security:
- Session-based authentication via UserSession and UserSessionHolder
- Protection: UserSessionFilter → RolesVerifyHandlerInterceptor → @RolesAllowed
- Check todoapp.security package for implementation details

### Code Style Guidelines

Software Design & Architecture:
- Follow Clean Architecture: Core independent of frameworks
- Use Use Case pattern: One interface per business function
- Use Repository pattern: Abstract data access, profile-based switching
- Domain layer defines interfaces, adapter layers implement
- Value objects for domain primitives (TodoId, UserId)
- Immutable design preferred where possible

Code Development & Quality:
- Use modern Java 21 features (records, pattern matching, text blocks)
- Use @Valid for request validation with record DTOs
- Use @RolesAllowed for method-level authorization
- Handle exceptions in GlobalControllerAdvice
- Write tests with Given/When/Then structure
- Controller tests: @WebMvcTest with MockMvc
- Domain tests: No Spring context, pure business logic

Naming Conventions:

Packages:
- core.{domain}.domain: Domain models and value objects
- core.{domain}.application: Use Case interfaces and implementations
- data: Repository implementations
- web: Controllers, configuration
- security: Authentication/authorization

Classes:
- Use Case interfaces: Short active verb phrase (AddTodo, FindTodos, RegisterUser, ChangeUserProfilePicture)
- Use Case implementations: Default*Manager
- Repository: {Entity}Repository
- Controller: {Name}Controller (MVC), {Name}RestController (REST)
- Exceptions: {Name}Exception extending SystemException

Methods:
- Use Cases: Business terms (add, find, modify, remove)
- Repository: JPA standard (findById, save, delete)

Error Handling:
- Domain exceptions extend SystemException
- GlobalControllerAdvice handles framework exceptions
- Localized error messages via MessageSource and messages*.properties

Formatting & Style:
- Follow Spring Boot and Spring MVC conventions
- Use constructor injection
- Mark Use Case implementations as @Service @Transactional
- Use @Profile for environment-specific beans
- Organize imports: java, jakarta, org, springframework, todoapp

---

## Client (Vanilla JavaScript)

### Technology stacks

- Language: JavaScript (ES modules)
- Build tool: Vite 5.x
- Template engine: Thymeleaf (server-side rendering)
- CSS: TodoMVC App CSS

### Project structure

- client/pages: HTML templates (todos.html, login.html, error.html)
- client/src/application: Business logic and state management
- client/src/ui: UI components and event handlers
- client/public: Static assets
- client/vite.config.js: Vite configuration

### Architecture principles

- Vanilla JavaScript with no frameworks
- Thymeleaf for initial HTML rendering
- AJAX for Web API calls
- EventSource for SSE (Server-Sent Events)
- Check existing components for patterns when adding new features

### Key patterns

Page structure:
- Thymeleaf templates for server-side rendering
- JavaScript modules for dynamic interactions
- Feature toggles control UI behavior

API communication:
- REST API calls via fetch
- JSON request/response format
- Error handling with HTTP status codes

Code Style Guidelines:

Code Development & Quality:
- Use ES modules (import/export)
- Use modern JavaScript features (async/await, destructuring, template literals)
- Separate concerns: application logic vs UI rendering
- Handle errors gracefully with user-friendly messages

Naming Conventions:
- Use camelCase for variables and functions
- Use descriptive names that reflect domain concepts
- Group related functionality in modules

Formatting & Style:
- Consistent indentation and spacing
- Clear separation between application and UI layers
- Follow existing code structure in src/application and src/ui
