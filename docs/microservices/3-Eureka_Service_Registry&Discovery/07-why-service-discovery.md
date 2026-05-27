# Why Service Discovery?

## Learning Objectives

After completing this document, you should be able to:

- Understand why Service Discovery is required in Microservices
- Explain the problems with hardcoded service URLs
- Understand dynamic infrastructure challenges
- Understand Service Registry concepts
- Visualize how services discover each other
- Explain the need for Eureka Server
- Answer Service Discovery interview questions confidently

---

# Introduction

In a Monolithic Application, all modules exist inside the same process.

Example:

```java
paymentService.processPayment();
inventoryService.updateStock();
```

Communication happens through direct method calls.

There is no need to locate another service because everything exists within the same application.

---

In a Microservices Architecture:

```text
User Service
Product Service
Inventory Service
Order Service
Payment Service
Notification Service
```

Each service runs independently.

Each service has:

- Its own process
- Its own port
- Its own deployment lifecycle
- Possibly multiple instances

Now a new problem appears:

> How does one service find another service?

This problem is known as:

# Service Discovery

---

# Understanding the Problem

Consider an Order Service.

When a customer places an order, Order Service needs to:

1. Check inventory
2. Process payment
3. Create order
4. Send notification

To do this, it must communicate with:

```text
Inventory Service
Payment Service
Notification Service
```

But where are these services running?

---

# Initial Approach: Hardcoded URLs

A beginner solution might be:

```properties
inventory.service.url=http://localhost:8081
payment.service.url=http://localhost:8082
```

Order Service uses these URLs.

Example:

```text
Order Service
      |
      |
      V
http://localhost:8081
```

This works initially.

---

# Why Hardcoded URLs Are Bad

Imagine Inventory Service moves.

Before:

```text
http://localhost:8081
```

After deployment:

```text
http://localhost:8095
```

Order Service still points to:

```text
8081
```

Requests fail.

---

Now configuration must be updated.

After update:

```properties
inventory.service.url=http://localhost:8095
```

Redeployment may be required.

This quickly becomes difficult to manage.

---

# Problem Becomes Worse in Production

Consider a real application.

Services may run on different machines.

Example:

```text
Inventory Service
10.10.1.25:8080

Payment Service
10.10.1.26:8080

Order Service
10.10.1.27:8080
```

Everything works.

---

A server crashes.

Inventory Service restarts on another machine.

New location:

```text
10.10.1.41:8080
```

Order Service still points to:

```text
10.10.1.25:8080
```

Communication fails.

---

# Auto Scaling Makes It Worse

Modern cloud platforms automatically create service instances.

Example:

Initially:

```text
Inventory Service

Instance 1
10.10.1.25
```

Heavy traffic arrives.

Platform automatically creates:

```text
Instance 1
10.10.1.25

Instance 2
10.10.1.42

Instance 3
10.10.1.53
```

Now which instance should Order Service call?

---

Hardcoded URLs become impossible to manage.

---

# Dynamic Infrastructure

Modern applications run on:

- AWS
- Azure
- Google Cloud
- Kubernetes
- Docker Containers

Infrastructure is highly dynamic.

Instances frequently:

- Start
- Stop
- Restart
- Scale Up
- Scale Down

IP addresses may change constantly.

---

Example:

Morning:

```text
Inventory Service
10.10.1.25
```

Afternoon:

```text
Inventory Service
10.10.1.42
```

Evening:

```text
Inventory Service
10.10.1.87
```

Service locations cannot be predicted.

---

# The Core Question

Instead of asking:

```text
What is the IP address?
```

we want to ask:

```text
Where is Inventory Service?
```

and receive the current location automatically.

This is the goal of Service Discovery.

---

# What is Service Discovery?

## Definition

Service Discovery is a mechanism that allows services to automatically locate and communicate with each other without hardcoded network addresses.

---

Instead of storing:

```text
http://10.10.1.25:8080
```

we use:

```text
inventory-service
```

The actual address is resolved dynamically.

---

# Service Registry

Service Discovery requires a central registry.

Think of it as a phone directory.

---

Example:

Phone Directory:

```text
John -> 9876543210

Rahul -> 9999999999

Amit -> 8888888888
```

You search by name.

You receive the number.

---

Similarly:

```text
inventory-service
```

returns:

```text
10.10.1.25:8080

10.10.1.42:8080

10.10.1.53:8080
```

---

This central directory is called:

# Service Registry

---

# What is a Service Registry?

A Service Registry stores information about running services.

Information includes:

- Service Name
- Host Address
- Port Number
- Health Status
- Metadata

Example:

```text
inventory-service

10.10.1.25:8080
10.10.1.42:8080
10.10.1.53:8080
```

---

# Service Registration

When a service starts:

It registers itself with the registry.

Example:

```text
Inventory Service Starts
```

Registers:

```text
inventory-service
10.10.1.25:8080
```

Registry stores this information.

---

# Service Discovery Flow

Step 1:

Inventory Service starts.

---

Step 2:

Inventory Service registers itself.

```text
inventory-service
10.10.1.25:8080
```

---

Step 3:

Order Service needs Inventory Service.

---

Step 4:

Order Service asks Registry:

```text
Where is inventory-service?
```

---

Step 5:

Registry responds:

```text
10.10.1.25:8080
```

---

Step 6:

Order Service sends request.

---

Flow:

```text
Inventory Service
       |
       |
 Register
       |
       V
Service Registry
       ^
       |
 Lookup
       |
Order Service
```

---

# Service Discovery Using Names

Without Service Discovery:

```text
http://10.10.1.25:8080
```

With Service Discovery:

```text
inventory-service
```

Service name becomes the identifier.

The actual address is determined automatically.

---

# Multiple Service Instances

Suppose Inventory Service scales.

Example:

```text
inventory-service

10.10.1.25:8080
10.10.1.42:8080
10.10.1.53:8080
```

Registry stores all instances.

---

Order Service asks:

```text
inventory-service
```

Registry returns available instances.

A Load Balancer selects one.

Example:

```text
Request 1 -> Instance 1

Request 2 -> Instance 2

Request 3 -> Instance 3
```

This enables scaling.

---

# Health Monitoring

A registry should know whether services are alive.

Suppose:

```text
Instance 2 crashes
```

Registry must stop returning it.

Otherwise requests fail.

---

Services periodically send:

```text
Heartbeat
```

messages.

Heartbeat means:

```text
I am alive
```

---

If heartbeats stop:

Registry removes the instance.

Example:

Before:

```text
Instance 1
Instance 2
Instance 3
```

After crash:

```text
Instance 1
Instance 3
```

Only healthy instances remain discoverable.

---

# Benefits of Service Discovery

---

## No Hardcoded URLs

Services communicate using names.

Example:

```text
inventory-service
```

instead of:

```text
10.10.1.25:8080
```

---

## Automatic Service Location

Location changes are handled automatically.

---

## Better Scalability

Multiple service instances are supported naturally.

---

## Fault Tolerance

Failed instances can be removed automatically.

---

## Cloud Friendly

Works well with:

- Containers
- Kubernetes
- Cloud Deployments
- Auto Scaling

---

## Simplified Configuration

No need to maintain numerous service addresses.

---

# Real-World Example

Imagine Netflix.

Services:

```text
User Service
Movie Service
Recommendation Service
Billing Service
Notification Service
```

Thousands of instances may run simultaneously.

IPs constantly change.

Manual management is impossible.

Service Discovery becomes essential.

---

# What Happens Without Service Discovery?

Challenges include:

- Hardcoded URLs
- Frequent configuration changes
- Failed deployments
- Difficult scaling
- Increased maintenance
- Service outages

Large systems become extremely difficult to manage.

---

# Service Discovery Components

Most Service Discovery systems contain:

## Service Provider

Registers itself.

Example:

```text
Inventory Service
```

---

## Service Registry

Stores service information.

Example:

```text
Eureka Server
```

---

## Service Consumer

Looks up services.

Example:

```text
Order Service
```

---

Architecture:

```text
Service Provider
        |
        |
   Registers
        |
        V
Service Registry
        ^
        |
        |
     Lookup
        |
Service Consumer
```

---

# Introducing Eureka

Spring Cloud provides:

# Eureka Server

Eureka acts as a Service Registry.

Responsibilities:

- Service Registration
- Service Discovery
- Health Tracking
- Instance Management

Example:

```text
Inventory Service
Product Service
Order Service
Payment Service
```

register themselves with Eureka.

Other services discover them using service names.

---

# How Future Communication Will Look

Instead of:

```java
http://10.10.1.25:8080/products
```

we will use:

```java
http://product-service/products
```

or

```java
@FeignClient(name="product-service")
```

Eureka automatically resolves the correct instance.

---

# Key Takeaways

- Microservices need a mechanism to locate each other.
- Hardcoded URLs do not work in dynamic environments.
- Cloud infrastructure constantly changes service locations.
- Service Discovery solves the location problem.
- Service Registry acts as a directory of active services.
- Services register themselves when they start.
- Consumers discover services dynamically.
- Health checks ensure failed services are removed.
- Eureka Server is Spring Cloud's Service Discovery solution.

---

# Interview Questions

## Q1. Why is Service Discovery needed in Microservices?

Because service locations change dynamically, and services need a way to locate each other without hardcoded addresses.

---

## Q2. What is a Service Registry?

A centralized directory that stores information about active service instances and their locations.

---

## Q3. What information is stored in a Service Registry?

Typically:

- Service Name
- Host/IP
- Port
- Health Status
- Metadata

---

## Q4. What is Service Registration?

The process where a service announces itself to the registry when it starts.

---

## Q5. What is a Heartbeat?

A periodic signal sent by a service to indicate that it is still running and healthy.

---

## Q6. What happens if a service instance crashes?

The registry stops receiving heartbeats and eventually removes the failed instance.

---

## Q7. Which Spring Cloud component provides Service Discovery?

Eureka Server.

---

# Next Topic

In the next document:

**08-eureka-architecture.md**

Topics:

- What is Eureka?
- Eureka Server Architecture
- Eureka Client Architecture
- Registration Process
- Discovery Process
- Heartbeats
- Self-Preservation Mode
- End-to-End Eureka Workflow