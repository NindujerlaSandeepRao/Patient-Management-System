# Patient Management System (Microservices Architecture)

## Overview

This project is a production-ready backend system designed using a microservices architecture to manage patient data efficiently. It demonstrates real-world backend engineering practices including service-to-service communication, API gateway routing, event-driven design, and containerized deployment.

The system is modular, scalable, and built to simulate how modern distributed applications are developed and deployed.

---

## Architecture

The application follows a distributed microservices architecture where each service is independently developed, deployed, and scaled.

### Core Components

* **API Gateway**

  * Single entry point for all client requests
  * Routes requests to appropriate microservices
  * Handles authentication and request filtering

* **Auth Service**

  * Manages user authentication
  * Generates and validates JWT tokens

* **Patient Service**

  * Core service responsible for patient data management
  * Acts as:

    * gRPC client (communicates with Billing Service)
    * Kafka producer (publishes patient events)

* **Billing Service**

  * Handles billing operations
  * Exposes gRPC server for inter-service communication

* **Analytics Service**

  * Kafka consumer
  * Processes patient data for analytics

* **Notification Service**

  * Kafka consumer
  * Sends notifications based on events

* **Kafka**

  * Enables asynchronous, event-driven communication

* **Docker Network**

  * All services are containerized and communicate within a shared network

---

## Communication Patterns

### REST API

* Used between Client → API Gateway → Microservices
* JSON-based communication

### gRPC

* Used for internal service-to-service communication
* Implemented between Patient Service (client) and Billing Service (server)
* Uses Protocol Buffers (`.proto`) for contract definition

### Kafka (Event-Driven)

* Patient Service publishes events
* Analytics and Notification services consume events asynchronously

---

## Technologies Used

* Java (Spring Boot)
* Spring Cloud Gateway
* gRPC & Protocol Buffers
* Apache Kafka
* Docker & Docker Compose
* JWT Authentication
* REST APIs
* JUnit 5
* RestAssured (Integration Testing)

---

## Features

* Microservices-based architecture
* API Gateway implementation
* JWT-based authentication and authorization
* gRPC-based inter-service communication
* Event-driven architecture using Kafka
* Fully Dockerized services
* Integration testing using RestAssured
* Clean separation of responsibilities

---

## Project Structure

patient-management-system/


├── api-gateway/

├── auth-service/

├── patient-service/

├── billing-service/

├── analytics-service/

├── notification-service/

├── grpc-requests/

├── integration-tests/

├── docker-compose.yml

---

## Docker Setup

### Run the Application

docker-compose up --build

### Stop the Application

docker-compose down

---

## Integration Testing

Integration tests are implemented using RestAssured.

### Test Coverage

* Valid login (JWT generation)
* Invalid login (unauthorized access)
* API response validation
* Status code verification
* Token-based authentication checks

Tests simulate real HTTP requests to running services, ensuring end-to-end functionality.

---

## gRPC Implementation

* Defined contracts using `.proto` files in `grpc-requests`
* Billing Service acts as gRPC server
* Patient Service acts as gRPC client
* Enables high-performance, strongly-typed communication

---

## What This Project Demonstrates

* Designing scalable microservices systems
* Implementing API Gateway pattern
* Secure authentication using JWT
* Efficient inter-service communication with gRPC
* Event-driven systems using Kafka
* Containerized deployment using Docker
* Writing real-world integration tests

---

## Future Improvements

* Service discovery (Eureka / Consul)
* Centralized logging (ELK stack)
* Monitoring (Prometheus + Grafana)
* Circuit breakers (Resilience4j)
* CI/CD pipeline integration

---

## Author

Nindujerla Sandeep Rao
