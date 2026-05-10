# JsonPlaceholder Client

A Spring Boot application that fetches posts from a public REST API and exports each post as a separate JSON file.

The project demonstrates:
- consuming external REST APIs using `RestTemplate`
- retry mechanism with Spring Retry
- custom error handling
- JSON serialization with Jackson
- file-based data export
- clean layered architecture in Spring Boot

---

## Architecture

Application flow:

1. Spring Boot application starts
2. `CommandLineRunner` triggers execution
3. `PostService` requests data via `JsonPlaceholderClient`
4. Data is fetched from JSONPlaceholder API
5. Each post is saved as a separate JSON file

---

## API Source

https://jsonplaceholder.typicode.com/posts

---

## Technologies

- Java 17+
- Spring Boot
- Spring Web (`RestTemplate`)
- Spring Retry
- Jackson
- Lombok
- Maven

---

## Configuration

All configuration is defined in `application.yml`:

```yaml
app:
  client:
    posts-url: https://jsonplaceholder.typicode.com/posts

  export:
    output-dir: output
```

## How to run

### 1. Prerequisites

Before running the application make sure you have installed:

- Java 17+
- Maven 3.8+
- Internet connection (required to fetch data from API)

---

### 2. Build and run the project

Run the following commands in the project root directory:

```bash
mvn clean install
mvn spring-boot:run
```