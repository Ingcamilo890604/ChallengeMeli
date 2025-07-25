# Product Catalog API

A RESTful API for a product catalog built with Spring Boot and Hexagonal Architecture.

## Features

- **Hexagonal Architecture**: Clean separation of concerns with domain and infrastructure layers
- **RESTful API**: Endpoints for managing products
- **API Documentation**: Swagger/OpenAPI documentation for the API
- **File-based Persistence**: Products stored in a JSON file
- **High Concurrency Support**: Virtual threads and concurrent data structures
- **Resilience Patterns**: Circuit breaker, retry, and rate limiter
- **Validation**: Input validation for API requests
- **Error Handling**: Custom exceptions and global exception handler

## Architecture

The application follows the Hexagonal Architecture (also known as Ports and Adapters) pattern:

- **Domain Layer**: Contains the business entities, use case interfaces, implementations, and ports
  - **Inbound Ports**: Use case interfaces that allow external actors to interact with the domain
  - **Outbound Ports**: Repository interfaces that allow the domain to interact with external systems
  - **Use Case Implementations**: Implementations of the use case interfaces that orchestrate the business logic
- **Infrastructure Layer**: Contains the adapters for external interfaces
  - **Inbound Adapters**: Controllers that adapt HTTP requests to use case calls
  - **Outbound Adapters**: Repository implementations that adapt the domain to persistence systems
  - **Configuration**: Configuration classes for various aspects of the application

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /api/products | Get all products |
| GET    | /api/products/{id} | Get a product by ID |
| POST   | /api/products | Create a new product |
| PUT    | /api/products/{id} | Update a product |
| DELETE | /api/products/{id} | Delete a product |

## Product Model

The product model includes:

- **id**: Unique identifier
- **title**: Product title
- **description**: Product description
- **price**: Product price
- **images**: List of image URLs
- **paymentMethods**: List of payment methods
- **seller**: Seller information
- **stock**: Available stock
- **rating**: Average rating
- **reviews**: List of customer reviews

## Running the Application

### Prerequisites

- Java 21 or higher
- Maven

### Steps

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

### API Documentation

The API documentation is available through Swagger UI. Once the application is running, you can access it at:

```
http://localhost:8080/swagger-ui/index.html
```

This provides an interactive documentation where you can:
- View all available endpoints
- See request and response models
- Test the API directly from the browser

## Concurrency and Resilience

The application is designed to handle high concurrency and be resilient to failures:

- **Virtual Threads**: Used for handling HTTP requests efficiently
- **Concurrent Data Structures**: ConcurrentHashMap for in-memory caching
- **Read-Write Locks**: Used for file access to prevent race conditions
- **Circuit Breaker**: Prevents cascading failures
- **Retry**: Automatically retries failed operations
- **Rate Limiter**: Limits the number of requests to prevent overload

## Error Handling

The application includes comprehensive error handling:

- **Custom Exceptions**: Domain-specific exceptions
- **Global Exception Handler**: Centralized exception handling
- **Validation**: Input validation with meaningful error messages

## Sample Data

The application includes sample data that is loaded on startup:

- Smartphone XYZ Pro
- Laptop UltraBook
- Wireless Headphones

Each product includes details such as price, images, payment methods, seller information, and reviews.