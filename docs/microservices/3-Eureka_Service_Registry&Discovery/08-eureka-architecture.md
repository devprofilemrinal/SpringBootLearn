# Eureka Architecture

## Learning Objectives

After completing this document, you should be able to:

- Understand what Eureka is
- Explain Eureka Server and Eureka Client
- Understand the registration process
- Understand the service discovery process
- Explain heartbeats and lease renewal
- Understand Eureka's registry
- Understand self-preservation mode
- Visualize how Eureka works internally
- Answer Eureka architecture interview questions confidently

---

# Introduction

In the previous chapter, we learned why Service Discovery is necessary.

We discovered that hardcoded service URLs do not work well in modern distributed systems because:

- Service instances start and stop dynamically
- IP addresses change
- Auto-scaling creates new instances
- Containers are constantly recreated

To solve this problem, Spring Cloud provides:

# Eureka

Eureka acts as a Service Registry where services:

- Register themselves
- Discover other services
- Send health updates

Before learning how to configure Eureka, we must first understand its architecture and internal workflow.

---

# What is Eureka?

## Definition

Eureka is a Service Discovery solution developed by Netflix and integrated into Spring Cloud.

It allows microservices to:

- Register themselves
- Discover other services
- Track healthy instances
- Support dynamic scaling

---

## Simple Explanation

Think of Eureka as a phone directory.

Example:

Instead of remembering phone numbers:

```text
Rahul -> 9876543210

Amit -> 9999999999

John -> 8888888888
```

You search by name.

The directory provides the number.

---

Similarly:

Instead of using:

```text
10.10.1.25:8080
```

we use:

```text
inventory-service
```

Eureka provides the actual location.

---

# Core Components of Eureka

Eureka consists of two major components:

1. Eureka Server
2. Eureka Client

---

# Eureka Server

## Definition

Eureka Server acts as the central Service Registry.

Responsibilities:

- Store service information
- Maintain service registry
- Track healthy instances
- Provide discovery information

---

Think of Eureka Server as:

```text
Service Directory
```

for the entire microservices ecosystem.

---

# Eureka Client

## Definition

Any application that communicates with Eureka is called a Eureka Client.

Examples:

```text
User Service
Product Service
Inventory Service
Order Service
Payment Service
Gateway
```

All of these are Eureka Clients.

---

Responsibilities:

- Register themselves
- Renew leases
- Fetch registry information
- Discover services

---

# High-Level Architecture

Example:

```text
                Eureka Server
                      |
    --------------------------------------
    |            |            |          |
User Service Product Service Order Service Payment Service
```

All services communicate with Eureka Server.

---

# Service Registration Process

When a service starts, it must register itself.

---

## Step 1

Inventory Service starts.

Example:

```text
inventory-service
```

---

## Step 2

Inventory Service sends registration information.

Example:

```text
Service Name
Host
Port
Metadata
```

---

Registration request:

```text
inventory-service
10.10.1.25
8080
```

---

## Step 3

Eureka stores this information.

Registry becomes:

```text
inventory-service

10.10.1.25:8080
```

---

## Step 4

Inventory Service is now discoverable.

Other services can locate it.

---

Registration Flow:

```text
Inventory Service
       |
       |
 Register
       |
       V
Eureka Server
```

---

# Service Discovery Process

Now Order Service needs Inventory Service.

---

## Step 1

Order Service asks Eureka:

```text
Where is inventory-service?
```

---

## Step 2

Eureka searches its registry.

---

## Step 3

Eureka returns available instances.

Example:

```text
10.10.1.25:8080
```

---

## Step 4

Order Service calls Inventory Service.

---

Discovery Flow:

```text
Order Service
      |
      | Lookup
      V
Eureka Server
      |
      | Instance Info
      V
Inventory Service
```

---

# Eureka Registry

The registry is Eureka's internal database.

It stores information about active services.

---

Example Registry:

```text
USER-SERVICE

10.10.1.10:8080

------------------

PRODUCT-SERVICE

10.10.1.20:8080
10.10.1.21:8080

------------------

ORDER-SERVICE

10.10.1.30:8080
```

---

Registry contains:

- Service Name
- Host
- Port
- Status
- Metadata

---

# Multiple Service Instances

Suppose Product Service scales.

Registry becomes:

```text
PRODUCT-SERVICE

10.10.1.20:8080
10.10.1.21:8080
10.10.1.22:8080
```

Multiple instances are stored.

---

This enables:

- Load Balancing
- High Availability
- Fault Tolerance

---

# Heartbeats

Registration alone is not enough.

How does Eureka know a service is still alive?

---

The answer is:

# Heartbeats

---

## What is a Heartbeat?

A heartbeat is a periodic signal sent by a service.

Meaning:

```text
I am alive
```

---

Example:

```text
Inventory Service
       |
Heartbeat
       |
Eureka
```

---

Services continuously send heartbeats while running.

---

# Lease Renewal

Heartbeats are also called:

```text
Lease Renewals
```

Each service maintains a lease with Eureka.

---

Example:

```text
Lease Duration = 90 seconds
```

Service must renew before expiration.

---

If renewal occurs:

```text
Service Remains Healthy
```

---

If renewal does not occur:

```text
Lease Expires
```

Eureka assumes the instance is unavailable.

---

# Instance Removal

Suppose Inventory Service crashes.

---

Before Crash:

```text
inventory-service

10.10.1.25:8080
```

---

After Crash:

No heartbeat received.

---

Lease expires.

---

Eureka removes the instance.

Registry becomes:

```text
inventory-service

(no instances)
```

---

Consumers will no longer receive that instance.

---

# Registry Fetching

Services need discovery information.

Therefore clients periodically fetch registry data.

---

Example:

Order Service downloads registry.

Registry:

```text
USER-SERVICE

PRODUCT-SERVICE

INVENTORY-SERVICE

PAYMENT-SERVICE
```

---

Now Order Service knows available services.

---

This reduces repeated calls to Eureka.

---

# Local Cache

Eureka Clients maintain a local cache.

---

Example:

```text
Order Service
```

stores:

```text
Inventory Service

10.10.1.25
10.10.1.26
10.10.1.27
```

locally.

---

Benefits:

- Faster lookup
- Reduced network traffic
- Improved performance

---

# Why Caching is Important

Imagine:

```text
100 Services
```

making discovery requests continuously.

Without caching:

```text
Heavy load on Eureka
```

---

With caching:

Most lookups happen locally.

Performance improves significantly.

---

# Eureka Self-Preservation Mode

One of Eureka's most important features.

---

# The Problem

Suppose network issues occur.

Example:

```text
100 Services Running
```

Network interruption prevents heartbeats.

---

Eureka suddenly stops receiving renewals.

---

Without protection:

Eureka may think:

```text
All services are dead
```

and remove them.

This would be disastrous.

---

# The Solution

Self-Preservation Mode

---

When Eureka notices a large drop in renewals:

It assumes:

```text
Network Problem
```

instead of:

```text
Massive Service Failure
```

---

Therefore:

Services are NOT immediately removed.

Registry is preserved.

---

Benefits:

- Prevent accidental removals
- Improve resilience
- Handle temporary network failures

---

# Service Startup Lifecycle

Let's see the complete startup process.

---

Step 1

Service starts.

Example:

```text
Inventory Service
```

---

Step 2

Connects to Eureka.

---

Step 3

Registers itself.

---

Step 4

Starts sending heartbeats.

---

Step 5

Fetches registry.

---

Step 6

Discovers other services.

---

Lifecycle:

```text
Start
  |
Register
  |
Heartbeat
  |
Fetch Registry
  |
Discover Services
```

---

# Complete Eureka Request Flow

Suppose customer places an order.

---

Step 1

Order Service receives request.

---

Step 2

Needs Inventory Service.

---

Step 3

Checks local cache.

---

Step 4

Finds:

```text
inventory-service

10.10.1.25
10.10.1.26
```

---

Step 5

Selects instance.

---

Step 6

Sends request.

---

Flow:

```text
Customer
   |
Order Service
   |
Local Cache
   |
Inventory Service
```

---

Eureka is not involved in every request.

It only provides discovery information.

This improves efficiency.

---

# Eureka Architecture Summary

```text
                    Eureka Server
                          |
      ------------------------------------------
      |            |            |             |
 User Service Product Service Order Service Payment Service
      |            |            |             |
      ------------------------------------------
               Registration
               Discovery
               Heartbeats
```

---

# Advantages of Eureka

## Dynamic Discovery

No hardcoded URLs.

---

## Automatic Registration

Services register automatically.

---

## Health Monitoring

Heartbeats indicate healthy instances.

---

## High Availability

Supports multiple service instances.

---

## Cloud Native

Works well with containers and cloud deployments.

---

## Reduced Configuration

Less manual maintenance.

---

# Limitations of Eureka

## Additional Infrastructure

Eureka itself must be managed.

---

## Network Dependency

Requires network connectivity.

---

## Eventual Consistency

Registry updates may take a short time to propagate.

---

## Extra Complexity

Introduces another distributed-system component.

---

# Key Takeaways

- Eureka is a Service Discovery solution from Netflix.
- Eureka Server acts as a Service Registry.
- Eureka Clients register themselves automatically.
- Services discover each other through Eureka.
- Eureka maintains a registry of active services.
- Heartbeats (Lease Renewals) indicate healthy instances.
- Failed services are removed after lease expiration.
- Clients cache registry information locally.
- Self-Preservation Mode prevents accidental mass removals.
- Eureka enables scalable and dynamic microservice environments.

---

# Interview Questions

## Q1. What is Eureka?

Eureka is a Service Discovery solution that allows services to register themselves and discover other services dynamically.

---

## Q2. What is the difference between Eureka Server and Eureka Client?

Eureka Server maintains the service registry, while Eureka Clients register themselves and discover other services.

---

## Q3. What is a heartbeat in Eureka?

A periodic renewal request sent by a service to indicate that it is still healthy and running.

---

## Q4. What happens if heartbeats stop?

The service lease expires, and Eureka eventually removes the instance from the registry.

---

## Q5. What is the Eureka Registry?

An internal directory that stores information about registered service instances.

---

## Q6. Why do Eureka Clients maintain a local cache?

To reduce network calls, improve lookup speed, and decrease load on Eureka Server.

---

## Q7. What is Self-Preservation Mode?

A protection mechanism that prevents Eureka from removing service instances during temporary network failures.

---

# Next Topic

In the next document:

**09-eureka-server-setup.md**

Topics:

- Creating a Eureka Server
- Maven Dependencies
- Spring Boot Configuration
- application.yml Configuration
- @EnableEurekaServer
- Running Eureka Server
- Understanding Eureka Dashboard