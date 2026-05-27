# Monolithic Architecture

## Learning Objectives

After completing this document, you should be able to:

- Understand what Monolithic Architecture is
- Explain how a monolithic application is structured
- Identify advantages and disadvantages of monolithic systems
- Understand deployment and scaling challenges
- Recognize why organizations moved towards Microservices
- Explain monolithic architecture in interviews and real-world discussions

---

# Introduction

Before Microservices became popular, most enterprise applications were built using a **Monolithic Architecture**.

Many successful systems initially started as monoliths because they are:

- Easy to build
- Easy to deploy
- Easy to understand
- Suitable for small and medium-sized applications

Even today, many organizations use monolithic applications successfully.

To understand why Microservices emerged, we must first understand the strengths and limitations of monolithic systems.

---

# What is Monolithic Architecture?

## Definition

A Monolithic Architecture is a software architecture where all application components are packaged and deployed as a single unit.

The entire application runs as one process and is usually deployed as one executable artifact.

---

## Simple Explanation

Imagine an e-commerce application containing:

- User Management
- Product Management
- Order Management
- Payment Processing
- Notification Service

In a monolithic architecture:

All these modules exist inside the same application.

Everything is:

- Built together
- Tested together
- Deployed together
- Scaled together

---

## Basic Representation

```text
+---------------------------------------+
|          E-Commerce Application       |
|                                       |
|  User Module                          |
|  Product Module                       |
|  Order Module                         |
|  Payment Module                       |
|  Notification Module                  |
|                                       |
+---------------------------------------+
                 |
                 |
          Single Database
```

All modules live within one application boundary.

---

# Characteristics of Monolithic Architecture

A monolithic application generally has the following characteristics:

## Single Codebase

Entire application exists in one repository.

Example:

```text
ecommerce-app/
|
|-- user/
|-- product/
|-- order/
|-- payment/
|-- notification/
```

Everything is maintained together.

---

## Single Deployment Unit

The application is packaged into:

- JAR
- WAR
- Executable

and deployed as a single artifact.

Example:

```text
ecommerce.jar
```

or

```text
ecommerce.war
```

---

## Shared Database

All modules commonly use the same database.

Example:

```text
ECommerceDB
|
|-- USERS
|-- PRODUCTS
|-- ORDERS
|-- PAYMENTS
```

---

## Tight Coupling

Modules often depend heavily on each other.

Example:

Order Module directly calls:

- User Module
- Product Module
- Payment Module

inside the same application.

---

# Example: Online Shopping Application

Consider an online shopping system.

Features include:

- User Registration
- Product Catalog
- Shopping Cart
- Order Placement
- Payments
- Notifications

Monolithic implementation:

```text
+------------------------------------------------+
|               Shopping Application             |
|                                                |
|  User Module                                   |
|  Product Module                                |
|  Cart Module                                   |
|  Order Module                                  |
|  Payment Module                                |
|  Notification Module                           |
|                                                |
+------------------------------------------------+
                     |
                     |
              Single Database
```

All modules run within the same application process.

---

# Internal Communication

Since everything exists within the same application:

Communication occurs using:

- Method calls
- Service classes
- Internal libraries

Example:

```java
orderService.placeOrder();
paymentService.processPayment();
notificationService.sendEmail();
```

No network communication is required.

This makes communication extremely fast.

---

# Typical Monolithic Application Layers

Most monolithic applications follow a layered architecture.

Example:

```text
Presentation Layer
        |
Business Layer
        |
Data Access Layer
        |
Database
```

---

## Presentation Layer

Responsible for:

- REST APIs
- Controllers
- User Interface

Example:

```java
@RestController
public class ProductController
```

---

## Business Layer

Contains business logic.

Example:

```java
ProductService
OrderService
PaymentService
```

---

## Data Access Layer

Responsible for database interaction.

Example:

```java
JpaRepository
DAO Classes
```

---

## Database Layer

Stores application data.

Example:

```text
MySQL
PostgreSQL
Oracle
```

---

# Advantages of Monolithic Architecture

Despite modern trends, monolithic architecture has many benefits.

---

## Simpler Development

Everything exists in one project.

Developers can:

- Navigate code easily
- Debug easily
- Understand dependencies quickly

This is ideal for beginners and small teams.

---

## Simple Deployment

Only one application is deployed.

Example:

```bash
java -jar ecommerce.jar
```

Deployment process is straightforward.

---

## Easy Testing

Entire application can be tested together.

Types:

- Unit Testing
- Integration Testing
- End-to-End Testing

become easier initially.

---

## Faster Internal Communication

Modules communicate through direct method calls.

Example:

```java
paymentService.processPayment();
```

No HTTP requests.

No network latency.

No serialization overhead.

---

## Easier Local Setup

Developers only need:

- Application
- Database

to start working.

No distributed environment setup required.

---

## Lower Operational Complexity

No need for:

- Service Discovery
- API Gateway
- Distributed Logging
- Inter-Service Communication
- Distributed Tracing

Infrastructure remains simple.

---

# Disadvantages of Monolithic Architecture

As applications grow, several problems begin to appear.

---

# Large Codebase

Over time:

- More features
- More modules
- More developers

lead to a very large codebase.

Example:

```text
500,000+ lines of code
```

Understanding the system becomes difficult.

---

# Tight Coupling

Modules become dependent on each other.

Example:

Order Module depends on:

- User Module
- Product Module
- Payment Module

Changes in one module may affect others.

---

# Difficult Maintenance

As complexity increases:

Developers spend more time:

- Understanding code
- Fixing bugs
- Managing dependencies

instead of building features.

---

# Slower Build Times

Large applications require:

- Longer compilation
- Longer packaging
- Longer testing

Example:

```text
Build Time:
2 minutes → 30 minutes
```

as the system grows.

---

# Deployment Challenges

Any small change requires redeploying the entire application.

Example:

Fixing a notification bug still requires deploying:

```text
User Module
Product Module
Order Module
Payment Module
Notification Module
```

even though only one module changed.

---

# Single Point of Failure

A failure in one module can affect the entire application.

Example:

Payment Module memory leak

may crash the whole application.

Result:

- Orders unavailable
- Products unavailable
- User login unavailable

Entire system impacted.

---

# Scaling Problems

One of the biggest challenges.

Suppose:

```text
Product Module = 80% traffic
Order Module   = 10% traffic
Payment Module = 10% traffic
```

Ideally only Product Module should scale.

But in monolithic architecture:

Entire application must be scaled.

Example:

```text
Instance 1
Instance 2
Instance 3
Instance 4
```

Every module gets duplicated.

This wastes resources.

---

# Technology Lock-In

Entire application usually uses one technology stack.

Example:

```text
Java + Spring Boot
```

Even if another module would benefit from:

```text
Python
Node.js
Go
```

introducing new technologies becomes difficult.

---

# Team Collaboration Issues

As teams grow:

Multiple developers modify the same codebase.

Problems:

- Merge conflicts
- Dependency issues
- Release coordination challenges

become common.

---

# Real-World Growth Problem

Imagine an e-commerce company.

Initial state:

```text
100 users/day
3 developers
```

Monolith works perfectly.

After success:

```text
5 million users/day
200 developers
```

Problems emerge:

- Slow deployments
- Difficult scaling
- Long testing cycles
- Increased downtime risk

Architecture starts becoming a bottleneck.

---

# Scaling a Monolith

## Vertical Scaling

Increase machine resources.

Before:

```text
4 CPU
8 GB RAM
```

After:

```text
16 CPU
64 GB RAM
```

Advantages:

- Easy implementation

Limitations:

- Hardware limits
- Expensive

---

## Horizontal Scaling

Run multiple application instances.

Example:

```text
Instance 1
Instance 2
Instance 3
Instance 4
```

Load Balancer distributes requests.

Advantages:

- Improved availability
- Better traffic handling

Limitations:

- Entire application gets replicated
- Resource wastage

---

# Why Industry Started Moving Away from Monoliths

As systems became larger:

Organizations needed:

- Independent deployments
- Independent scaling
- Faster releases
- Better fault isolation
- Smaller teams
- Technology flexibility

Monolithic architecture struggled to satisfy these requirements.

This led to the emergence of:

# Microservices Architecture

where applications are divided into smaller independently deployable services.

---

# Monolith vs Microservices (High Level)

| Feature | Monolith | Microservices |
|----------|-----------|---------------|
| Deployment | Single | Independent |
| Scaling | Entire Application | Individual Services |
| Database | Usually Shared | Usually Separate |
| Failure Impact | Entire System | Limited Service |
| Development Complexity | Low Initially | Higher |
| Operational Complexity | Low | High |
| Team Independence | Limited | High |
| Technology Flexibility | Low | High |

---

# When Should We Use Monolithic Architecture?

Monolithic architecture is still a good choice when:

- Team size is small
- Product is new
- Requirements change frequently
- Traffic is low or moderate
- Operational simplicity is important

Many startups successfully begin with a monolith.

Prematurely adopting Microservices can introduce unnecessary complexity.

---

# Key Takeaways

- Monolithic Architecture packages the entire application as one deployable unit.
- All modules run within the same process.
- Communication occurs through direct method calls.
- Monoliths are simpler to build, deploy, and manage initially.
- As applications grow, maintenance and scaling become difficult.
- Tight coupling and large codebases increase complexity.
- Independent scaling is not possible.
- Monolith limitations eventually led to the adoption of Microservices.

---

# Interview Questions

## Q1. What is Monolithic Architecture?

A software architecture where all application components are packaged, deployed, and executed as a single application unit.

---

## Q2. What are the advantages of Monolithic Architecture?

- Simple development
- Easy deployment
- Faster internal communication
- Easier testing
- Lower operational complexity

---

## Q3. What are the disadvantages of Monolithic Architecture?

- Tight coupling
- Difficult scaling
- Large codebase
- Slower deployments
- Single point of failure

---

## Q4. Why is scaling difficult in a monolith?

Because individual modules cannot be scaled independently. The entire application must be replicated even if only one module experiences heavy traffic.

---

## Q5. Is Monolithic Architecture bad?

No. Monoliths are often the best choice for small applications, startups, and teams that want operational simplicity.

---

# Next Topic

In the next document:

**03-introduction-to-microservices.md**

Topics:

- What are Microservices?
- Characteristics of Microservices
- Benefits of Microservices
- Challenges of Microservices
- Real-world Examples
- Monolith vs Microservices Detailed Comparison