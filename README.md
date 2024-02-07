# Recommendation Service

## Overview
Recommendation Service is a project that provides analytics data for cryptocurrencies.

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database
- JUnit and Mockito

## Getting Started

### Clone the repository
To get started, clone this repository.

### Run the application
Once the build is successful, you can run the application using:

```bash
mvn spring-boot:run
```

### Documentation
The OpenApi will be available at `http://localhost:8080/recommendation-service/swagger-ui/index.html`.


## Running Tests
To run the test suite, execute:

```bash
mvn test
```

## Docker
`docker build -t your-image-name .` \
`docker run -p 8080:8080 your-image-name`

### Short description
On application startup data are loaded to db. <- Batch loading should be added. 

When data are loaded we can call through RestApi.
