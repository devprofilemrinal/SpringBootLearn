# What is Software Architecture?

## Learning Objectives

After completing this document, you should be able to:

- Understand what Software Architecture is
- Explain why architecture is important
- Differentiate between functional and non-functional requirements
- Understand qualities of a good software system
- Explain scalability, availability, reliability, and maintainability
- Understand how architecture influences business success

---

# Introduction

Before learning Microservices, Eureka, API Gateway, or any distributed system concepts, it is important to understand **Software Architecture**.

Every software application is built using some architectural style.

Just like a building requires a blueprint before construction, software requires an architecture before development begins.

Architecture acts as the high-level blueprint of a software system.

It describes:

- Components of the system
- Communication between components
- Technology choices
- Data flow
- Deployment strategy
- Scalability approach
- Security considerations

Without proper architecture, applications become difficult to maintain, scale, and enhance.

---

# What is Software Architecture?

## Definition

Software Architecture is the high-level design of a software system that defines:

- Structure of the application
- Components/modules
- Relationships between components
- Communication mechanisms
- Design principles
- Technology decisions

It serves as a blueprint that guides development teams in building software systems.

---

## Simple Analogy

Imagine constructing a house.

Before construction begins:

- Rooms are planned
- Electrical wiring is designed
- Water pipelines are designed
- Doors and windows are planned

This blueprint is architecture.

Similarly, before coding:

- APIs are planned
- Databases are designed
- Services are identified
- Communication methods are decided

This becomes software architecture.

---

# Why Do We Need Software Architecture?

Many beginners think software development is only about writing code.

In reality, architecture determines whether software can survive future business growth.

Architecture helps us answer questions such as:

### Scalability

Can the application handle 10 users today and 1 million users tomorrow?

### Reliability

Will the application continue working when failures occur?

### Availability

Can users access the application at all times?

### Security

How will sensitive information be protected?

### Maintainability

Can developers easily modify the system in the future?

### Performance

Can requests be processed quickly?

---

# Example Without Architecture

Suppose an e-commerce application starts with:

- Product Management
- User Management
- Order Processing

Initially:

- Few users
- Few developers
- Small database

Everything works well.

After business growth:

- Millions of users
- Hundreds of developers
- Large codebase

Problems begin:

- Slow deployments
- Difficult debugging
- Frequent outages
- Complex code dependencies

These issues often occur because architectural planning was ignored.

---

# Goals of Software Architecture

A good architecture should provide:

| Goal | Description |
|--------|-------------|
| Scalability | Handle increasing load |
| Availability | System remains accessible |
| Reliability | System works correctly |
| Maintainability | Easy to modify |
| Performance | Fast response times |
| Security | Protect system and data |
| Flexibility | Adapt to changes |
| Testability | Easy testing |

---

# Functional Requirements

## Definition

Functional requirements describe:

> What the system should do.

These requirements define the actual business functionality.

---

## Examples

For an Online Shopping Application:

### User Features

- User registration
- User login
- Password reset

### Product Features

- View products
- Search products
- Filter products

### Order Features

- Place order
- Cancel order
- Track order

### Payment Features

- Online payment
- Refund processing

These are all functional requirements.

---

## Sample Requirement

"The system should allow users to place orders."

This describes functionality.

Therefore it is a functional requirement.

---

# Non-Functional Requirements (NFR)

## Definition

Non-functional requirements describe:

> How well the system should perform.

They define quality attributes rather than business functionality.

---

## Examples

Instead of:

"User can place an order."

We ask:

- How fast?
- How secure?
- How many users?
- How reliable?

These become non-functional requirements.

---

## Common Non-Functional Requirements

| Requirement | Example |
|------------|---------|
| Performance | Response within 2 seconds |
| Scalability | Support 1 million users |
| Security | Data encryption |
| Availability | 99.99% uptime |
| Reliability | No data loss |
| Maintainability | Easy code modifications |
| Usability | Easy user experience |
| Fault Tolerance | Recover from failures |

---

# Functional vs Non-Functional Requirements

| Functional Requirement | Non-Functional Requirement |
|------------------------|---------------------------|
| What system does | How system performs |
| Business behavior | Quality attribute |
| User registration | Registration in less than 2 seconds |
| Order placement | Handle 10,000 orders/minute |
| Payment processing | 99.99% availability |

---

# Important Architectural Characteristics

These qualities heavily influence architectural decisions.

---

# Scalability

## Definition

Scalability is the ability of a system to handle increasing workload without performance degradation.

---

## Example

Today:

- 100 users

Tomorrow:

- 100,000 users

The application should continue functioning efficiently.

---

## Why Scalability Matters

Business growth means:

- More customers
- More requests
- More transactions
- More data

A scalable architecture accommodates this growth.

---

## Types of Scaling

### Vertical Scaling

Increase resources of a machine:

Before:

- 4 GB RAM
- 2 CPU cores

After:

- 16 GB RAM
- 8 CPU cores

This is vertical scaling.

---

### Horizontal Scaling

Add more machines.

Before:

- 1 server

After:

- 10 servers

Traffic gets distributed across servers.

This is horizontal scaling.

---

## Example

Netflix serves millions of users worldwide.

To support huge traffic, Netflix uses extensive horizontal scaling.

---

# Availability

## Definition

Availability measures how often a system remains accessible to users.

---

## Example

Users should be able to:

- Login
- Browse products
- Place orders

Even during high traffic.

---

## Availability Formula

Availability is often measured as:

Availability = Uptime / Total Time

---

## Common Availability Targets

| Availability | Downtime Per Year |
|-------------|------------------|
| 99% | ~3.65 days |
| 99.9% | ~8.76 hours |
| 99.99% | ~52 minutes |
| 99.999% | ~5 minutes |

---

## High Availability Techniques

- Multiple servers
- Load balancing
- Database replication
- Automatic failover
- Distributed architecture

---

# Reliability

## Definition

Reliability is the ability of a system to consistently perform correctly over time.

---

## Example

When a customer places an order:

Expected outcome:

- Order stored
- Payment processed
- Confirmation generated

The system should produce correct results every time.

---

## Reliability Problems

Examples include:

- Lost transactions
- Duplicate orders
- Missing payments
- Data corruption

Reliable systems minimize such failures.

---

# Maintainability

## Definition

Maintainability refers to how easily software can be:

- Modified
- Fixed
- Enhanced
- Tested

---

## Why It Matters

Applications evolve continuously.

Businesses may request:

- New features
- Bug fixes
- Integrations
- Security updates

Poor maintainability increases development cost.

---

## Characteristics of Maintainable Systems

### Modular Design

Code separated into logical modules.

### Loose Coupling

Components have minimal dependencies.

### High Cohesion

Each module has a single responsibility.

### Clean Code

Readable and understandable code.

### Proper Documentation

Easy onboarding for new developers.

---

# Architecture and Business Growth

Architecture directly affects business success.

Poor architecture may cause:

- Downtime
- Slow systems
- Customer dissatisfaction
- Revenue loss

Good architecture enables:

- Faster development
- Better performance
- Easier scaling
- Higher customer satisfaction

---

# Real-World Example

Imagine an online food delivery platform.

Functional Requirements:

- Browse restaurants
- Place orders
- Make payments

Non-Functional Requirements:

- 2-second response time
- 99.99% uptime
- Secure payments
- Support 1 million users

Architecture decisions are made primarily to satisfy these non-functional requirements.

---

# Key Takeaways

- Software Architecture is the blueprint of a software system.
- Architecture defines components, communication, and technology choices.
- Functional requirements describe what a system does.
- Non-functional requirements describe how well a system performs.
- Scalability enables growth.
- Availability keeps systems accessible.
- Reliability ensures correctness.
- Maintainability simplifies future changes.
- Good architecture supports business growth and long-term success.

---

# Interview Questions

### Q1. What is Software Architecture?

Software Architecture is the high-level design of a software system that defines components, interactions, technologies, and design decisions.

---

### Q2. Why is Software Architecture important?

It helps achieve scalability, reliability, maintainability, security, and performance while supporting future business growth.

---

### Q3. What are Functional Requirements?

Requirements describing what the system should do, such as login, registration, order placement, and payment processing.

---

### Q4. What are Non-Functional Requirements?

Requirements describing system quality attributes such as scalability, availability, security, and performance.

---

### Q5. Difference between Scalability and Availability?

Scalability focuses on handling increased load, while availability focuses on keeping the system accessible to users.

---

### Q6. What is Maintainability?

Maintainability is the ease with which software can be modified, enhanced, tested, and fixed over time.

---

# Next Topic

In the next document, we will study:

**02-monolithic-architecture.md**

Topics:

- What is Monolithic Architecture
- Structure of a Monolithic Application
- Advantages
- Disadvantages
- Scaling Challenges
- Why Industry Started Moving Towards Microservices