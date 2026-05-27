# Introduction to Microservices

## Learning Objectives

After completing this document, you should be able to:

- Understand what Microservices are
- Explain the core principles of Microservice Architecture
- Identify the characteristics of a microservice-based system
- Understand the benefits and challenges of microservices
- Compare Microservices with Monolithic Architecture
- Understand why organizations adopt microservices
- Explain microservices confidently in interviews

---

# Introduction

In the previous chapter, we learned about Monolithic Architecture.

While monoliths are simple and effective for small applications, they become difficult to manage as systems grow.

Organizations such as Netflix, Amazon, Uber, Spotify, and many others faced challenges including:

- Large codebases
- Slow deployments
- Scaling limitations
- Team coordination issues
- High impact of failures

To solve these problems, a new architectural style became popular:

# Microservices Architecture

Instead of building one large application, the application is divided into multiple smaller services that work together.

---

# What are Microservices?

## Definition

Microservices Architecture is an architectural style where an application is built as a collection of small, independent services.

Each service:

- Performs a specific business function
- Runs independently
- Can be deployed independently
- Can scale independently
- Owns its own logic
- Communicates with other services through APIs

---

## Simple Explanation

Consider an E-Commerce application.

In a Monolith:

```text
E-Commerce Application
|
|-- User Module
|-- Product Module
|-- Order Module
|-- Payment Module
|-- Notification Module
```

Everything exists inside one application.

---

In a Microservices Architecture:

```text
User Service
Product Service
Order Service
Payment Service
Notification Service
```

Each service becomes an independent application.

Each service can:

- Start independently
- Stop independently
- Deploy independently
- Scale independently

---

# Visualizing a Microservices System

```text
                Client
                   |
                   |
             API Gateway
                   |
 ------------------------------------------------
 |              |             |                |
User Service Product Service Order Service Payment Service
                   |
             Notification Service
```

Each service performs a specific business responsibility.

---

# Why Were Microservices Introduced?

Microservices emerged to solve common problems found in large monolithic systems.

---

## Problem 1: Independent Deployment

Monolith:

```text
Notification bug fixed
```

Still requires redeploying:

```text
Entire Application
```

Microservices:

```text
Only Notification Service
```

needs deployment.

---

## Problem 2: Independent Scaling

Suppose:

```text
Product Service = Heavy Traffic
Order Service = Moderate Traffic
Payment Service = Low Traffic
```

Monolith:

Scale everything.

Microservices:

Scale only Product Service.

---

## Problem 3: Team Independence

Large organizations may have:

- Hundreds of developers
- Multiple teams

Microservices allow teams to own specific services.

Example:

```text
Team A -> User Service
Team B -> Product Service
Team C -> Payment Service
```

Each team works independently.

---

## Problem 4: Fault Isolation

Suppose Payment Service crashes.

Monolith:

Entire application may fail.

Microservices:

Only Payment Service is affected.

Other services continue functioning.

---

# Core Principles of Microservices

Several principles define a good microservices architecture.

---

# Single Responsibility

Every service should have one primary business responsibility.

Example:

```text
User Service
```

Responsible for:

- Registration
- Login
- Profile Management

Nothing else.

---

Example:

```text
Payment Service
```

Responsible for:

- Payment Processing
- Refunds
- Transaction Validation

Nothing else.

---

# Business Capability Based Design

Services should be designed around business functions.

Examples:

```text
Inventory Service
Order Service
Product Service
Shipping Service
Notification Service
```

Each represents a business capability.

---

# Independent Deployment

Each service can be deployed separately.

Example:

```text
Payment Service v2
```

can be deployed without affecting:

```text
User Service
Product Service
Order Service
```

---

# Loose Coupling

Services should minimize dependencies.

Bad:

```text
Order Service tightly depends on User Service internals
```

Good:

```text
Order Service communicates through API contracts
```

Services remain independent.

---

# High Cohesion

All functionality within a service should be closely related.

Example:

User Service should contain:

- Login
- Registration
- Password Reset

Not:

- Payment Processing
- Product Search

---

# Characteristics of Microservices

---

# Small and Focused

Each service performs a limited business function.

Examples:

```text
User Service
Inventory Service
Payment Service
```

instead of one giant application.

---

# Independent Deployability

Each service can be:

- Built separately
- Tested separately
- Released separately

without impacting others.

---

# Independent Scalability

Heavy traffic services can scale independently.

Example:

```text
10 Product Service instances
2 Payment Service instances
3 User Service instances
```

based on demand.

---

# Decentralized Data Management

Typically each service owns its own database.

Example:

```text
User Service
|
User Database

Product Service
|
Product Database

Order Service
|
Order Database
```

This prevents tight database coupling.

---

# Technology Flexibility

Different services can use different technologies if required.

Example:

```text
User Service -> Java

Recommendation Service -> Python

Analytics Service -> Go
```

Each service can use the most suitable technology.

---

# API-Based Communication

Services communicate through APIs.

Common approaches:

- REST APIs
- gRPC
- Message Queues
- Event Streaming

Example:

```text
Order Service
     |
 REST API
     |
Inventory Service
```

---

# Example: Online Shopping Application

Let's break an e-commerce platform into microservices.

---

## User Service

Responsibilities:

- Registration
- Login
- Profile Management

Database:

```text
UserDB
```

---

## Product Service

Responsibilities:

- Product Catalog
- Product Search
- Product Information

Database:

```text
ProductDB
```

---

## Inventory Service

Responsibilities:

- Stock Management
- Availability Checks

Database:

```text
InventoryDB
```

---

## Order Service

Responsibilities:

- Create Orders
- Update Orders
- Cancel Orders

Database:

```text
OrderDB
```

---

## Payment Service

Responsibilities:

- Payment Processing
- Refunds
- Transaction Records

Database:

```text
PaymentDB
```

---

## Notification Service

Responsibilities:

- Email Notifications
- SMS Notifications
- Push Notifications

Database:

```text
NotificationDB
```

---

# Benefits of Microservices

---

# Faster Development

Teams work independently.

Development becomes parallel.

---

# Independent Deployment

Deploy only changed services.

Reduced deployment risk.

---

# Better Scalability

Scale only services requiring additional resources.

More cost-efficient.

---

# Improved Fault Isolation

Failure in one service does not necessarily bring down the entire application.

---

# Technology Freedom

Different services may use different technologies.

Teams can choose the most suitable stack.

---

# Easier Team Ownership

Each team owns a service.

Clear responsibilities.

Reduced coordination overhead.

---

# Better Maintainability

Smaller codebases are easier to:

- Understand
- Modify
- Test
- Debug

---

# Challenges of Microservices

Microservices solve many problems but introduce new challenges.

---

# Increased Complexity

Instead of one application:

```text
1 Application
```

you may have:

```text
20 Services
50 Services
100 Services
```

System complexity increases.

---

# Service Discovery

Services need a mechanism to find each other.

Example:

```text
Order Service
needs
Inventory Service
```

How does it find the correct instance?

This leads to:

# Eureka Service Discovery

which we will learn later.

---

# Network Communication

Method calls become network calls.

Example:

Monolith:

```java
inventoryService.checkStock();
```

Microservices:

```http
GET /inventory/check
```

Network introduces:

- Latency
- Timeouts
- Failures

---

# Distributed Debugging

Tracing a request becomes harder.

Example:

```text
Gateway
 |
Order Service
 |
Inventory Service
 |
Payment Service
 |
Notification Service
```

Finding failures becomes more complex.

---

# Distributed Data Challenges

Each service owns its own database.

Transactions across services become difficult.

---

# Monitoring Challenges

Need monitoring for:

- Services
- APIs
- Databases
- Infrastructure

across the entire system.

---

# Monolith vs Microservices

| Feature | Monolith | Microservices |
|----------|-----------|--------------|
| Deployment | Single Deployment | Independent Deployment |
| Scaling | Entire Application | Individual Service |
| Codebase | Single Large Codebase | Multiple Small Codebases |
| Database | Shared Database | Service-Owned Databases |
| Fault Isolation | Low | High |
| Team Independence | Limited | High |
| Technology Choice | Limited | Flexible |
| Operational Complexity | Low | High |
| Development Speed | Slows with Growth | Better for Large Teams |

---

# When Should You Use Microservices?

Microservices are useful when:

- Application is large
- Traffic is high
- Multiple teams exist
- Independent deployment is required
- Scalability requirements are significant

---

# When Should You Avoid Microservices?

Microservices may not be suitable when:

- Team size is small
- Product is in early stages
- Traffic is low
- Infrastructure expertise is limited
- Simplicity is preferred

A well-designed monolith is often a better starting point.

---

# Real-World Examples

Companies using Microservices:

- Netflix
- Amazon
- Uber
- Spotify
- Airbnb
- PayPal

These organizations operate large-scale distributed systems where independent scaling and deployment are essential.

---

# Key Takeaways

- Microservices divide an application into multiple independent services.
- Each service focuses on a single business capability.
- Services communicate through APIs.
- Services can be deployed and scaled independently.
- Microservices improve scalability, maintainability, and team autonomy.
- Microservices introduce operational and distributed-system complexity.
- Service Discovery becomes necessary in a distributed environment.
- Tools like Eureka, API Gateway, Feign Client, and Load Balancers help manage this complexity.

---

# Interview Questions

## Q1. What are Microservices?

Microservices are an architectural style where an application is divided into small, independent services that communicate through APIs.

---

## Q2. What are the advantages of Microservices?

- Independent deployment
- Independent scaling
- Better fault isolation
- Faster development
- Technology flexibility

---

## Q3. What is meant by Loose Coupling?

Services interact through well-defined APIs and do not depend on each other's internal implementation.

---

## Q4. What is High Cohesion?

A service contains closely related functionality focused on a single business responsibility.

---

## Q5. Why is Service Discovery needed in Microservices?

Because service instances can change dynamically, services need a mechanism to locate each other automatically.

---

## Q6. Are Microservices always better than Monoliths?

No. Microservices are beneficial for large, complex systems but add significant operational complexity. Smaller applications often benefit from a monolithic architecture.

---

# Next Topic

In the next document:

**04-microservice-communication-patterns.md**

Topics:

- Why Services Need Communication
- Synchronous Communication
- Asynchronous Communication
- REST APIs
- Messaging Queues
- Event-Driven Architecture
- Request-Response Pattern
- Real-World Communication Flows