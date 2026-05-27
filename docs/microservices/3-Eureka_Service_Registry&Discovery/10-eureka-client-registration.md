# Registering Microservices with Eureka (Eureka Client Registration)

## Learning Objectives

After completing this document, you should be able to:

- Understand what a Eureka Client is
- Configure a Spring Boot application as a Eureka Client
- Register a microservice with Eureka Server
- Understand the registration lifecycle
- Understand heartbeats and lease renewal
- Verify registration in Eureka Dashboard
- Understand service metadata
- Explain Eureka Client registration flow in interviews

---

# Introduction

In the previous chapter, we created a Eureka Server.

The Eureka Server acts as a central registry where services can:

- Register themselves
- Discover other services
- Send health updates

However, a Eureka Server alone is not enough.

For Service Discovery to work:

```text
Services must register themselves with Eureka
```

These services are called:

# Eureka Clients

---

# What is a Eureka Client?

## Definition

A Eureka Client is any application that communicates with Eureka Server for:

- Registration
- Discovery
- Heartbeats
- Registry fetching

---

Examples:

```text
User Service

Product Service

Inventory Service

Order Service

Payment Service

Notification Service

API Gateway
```

All of these can act as Eureka Clients.

---

# Architecture Overview

```text
                Eureka Server
                      |
     ------------------------------------
     |          |          |            |
 Product    Order     Payment      User
 Service    Service   Service      Service
```

Each service registers with Eureka.

---

# What Happens During Registration?

When a service starts:

1. Application starts
2. Connects to Eureka Server
3. Sends registration information
4. Registry is updated
5. Service becomes discoverable

---

Example:

```text
Product Service Starts
        |
        V
Registers with Eureka
        |
        V
Appears in Registry
```

---

# Step 1: Create a Spring Boot Service

Suppose we create:

```text
product-service
```

Project Structure:

```text
product-service
|
|-- ProductServiceApplication.java
|
|-- application.yml
|
|-- pom.xml
```

---

# Step 2: Add Eureka Client Dependency

Add dependency:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>
        spring-cloud-starter-netflix-eureka-client
    </artifactId>
</dependency>
```

---

Purpose:

Provides:

- Registration support
- Service discovery support
- Heartbeat mechanism
- Registry fetching

---

# Step 3: Configure Application Name

Add:

```yaml
spring:
  application:
    name: product-service
```

---

Why is this important?

The service name becomes the identifier inside Eureka.

Example:

```text
PRODUCT-SERVICE
```

appears in the registry.

---

# Step 4: Configure Eureka Server Location

Add:

```yaml
eureka:
  client:
    service-url:
      defaultZone:
        http://localhost:8761/eureka/
```

---

Meaning:

```text
Connect to Eureka Server
running on localhost:8761
```

---

# Complete Basic Configuration

```yaml
server:
  port: 8081

spring:
  application:
    name: product-service

eureka:
  client:
    service-url:
      defaultZone:
        http://localhost:8761/eureka/
```

---

# Understanding Each Configuration

---

## server.port

```yaml
server:
  port: 8081
```

Service runs on:

```text
localhost:8081
```

---

## spring.application.name

```yaml
spring:
  application:
    name: product-service
```

Registered as:

```text
PRODUCT-SERVICE
```

inside Eureka.

---

## defaultZone

```yaml
defaultZone:
  http://localhost:8761/eureka/
```

Location of Eureka Server.

---

# Step 5: Create Main Class

Example:

```java
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(
            ProductServiceApplication.class,
            args
        );

    }
}
```

---

Notice:

No special annotation is required.

Spring Boot automatically enables Eureka Client functionality when:

- Eureka dependency exists
- Configuration is present

---

# Starting the Service

Run:

```bash
mvn spring-boot:run
```

or start from IDE.

---

Startup sequence:

```text
Application Started
        |
Connect Eureka
        |
Register Service
        |
Start Heartbeats
        |
Ready
```

---

# Registration Request

When Product Service starts:

It sends information to Eureka.

Example:

```text
Service Name:
PRODUCT-SERVICE

Host:
localhost

Port:
8081
```

---

Eureka stores:

```text
PRODUCT-SERVICE
localhost:8081
```

---

# Verifying Registration

Open:

```text
http://localhost:8761
```

---

Dashboard now displays:

```text
Applications

PRODUCT-SERVICE
```

---

Example:

```text
PRODUCT-SERVICE

n/a (1)
```

Meaning:

```text
1 active instance
```

is registered.

---

# Service Registration Lifecycle

Let's examine the complete lifecycle.

---

## Step 1

Application starts.

Example:

```text
Product Service
```

---

## Step 2

Reads configuration.

```yaml
application.yml
```

---

## Step 3

Locates Eureka Server.

```yaml
defaultZone
```

---

## Step 4

Creates registration information.

Contains:

- Service Name
- Host
- Port
- Metadata

---

## Step 5

Sends registration request.

---

## Step 6

Eureka stores instance.

---

## Step 7

Service becomes discoverable.

---

Flow:

```text
Start
  |
Read Config
  |
Connect Eureka
  |
Register
  |
Become Discoverable
```

---

# What Information Gets Registered?

Eureka stores an object called:

```text
Instance Information
```

---

Contains:

```text
Application Name

Host Name

IP Address

Port

Status

Metadata
```

---

Example:

```text
PRODUCT-SERVICE

localhost

127.0.0.1

8081

UP
```

---

# Multiple Service Instances

Suppose Product Service scales.

Instance 1:

```text
localhost:8081
```

Instance 2:

```text
localhost:8082
```

Instance 3:

```text
localhost:8083
```

---

Registry becomes:

```text
PRODUCT-SERVICE

localhost:8081

localhost:8082

localhost:8083
```

---

Now multiple instances exist.

---

Benefits:

- Load Balancing
- High Availability
- Better Performance

---

# Heartbeats

Registration alone is insufficient.

Eureka must know if the service is still alive.

---

Solution:

# Heartbeats

---

Heartbeat meaning:

```text
I am still running
```

---

Example:

```text
Product Service
       |
Heartbeat
       |
Eureka Server
```

---

# Lease Renewal

Heartbeats are called:

```text
Lease Renewals
```

---

Every instance maintains a lease.

Example:

```text
Lease Duration = 90 seconds
```

---

Service periodically renews lease.

Example:

```text
Every 30 seconds
```

Heartbeat sent.

---

Result:

```text
Status = UP
```

---

# What If Heartbeats Stop?

Suppose service crashes.

Example:

```text
Product Service Down
```

---

Heartbeats stop.

---

Lease expires.

---

Eureka removes instance.

Before:

```text
PRODUCT-SERVICE

localhost:8081
```

---

After removal:

```text
PRODUCT-SERVICE

(no instances)
```

---

Clients stop using that instance.

---

# Service Status Values

Common Eureka statuses:

---

## UP

Service is healthy.

```text
UP
```

---

## DOWN

Service unavailable.

```text
DOWN
```

---

## STARTING

Service startup in progress.

```text
STARTING
```

---

## OUT_OF_SERVICE

Temporarily disabled.

```text
OUT_OF_SERVICE
```

---

## UNKNOWN

Health cannot be determined.

```text
UNKNOWN
```

---

# Registry Fetching

Besides registration, clients also download registry information.

---

Example:

Product Service downloads:

```text
ORDER-SERVICE

PAYMENT-SERVICE

INVENTORY-SERVICE
```

---

This enables future service discovery.

---

# Client Local Cache

Registry information is cached locally.

Example:

```text
Product Service Cache

ORDER-SERVICE

PAYMENT-SERVICE

USER-SERVICE
```

---

Benefits:

- Faster lookups
- Reduced network traffic
- Better performance

---

# Registration Flow Example

Let's follow a real scenario.

---

Step 1

Start Product Service.

```text
localhost:8081
```

---

Step 2

Connect to Eureka.

```text
localhost:8761
```

---

Step 3

Register:

```text
PRODUCT-SERVICE
localhost:8081
```

---

Step 4

Eureka stores entry.

---

Step 5

Dashboard updates.

---

Step 6

Heartbeats begin.

---

Final state:

```text
PRODUCT-SERVICE
Status: UP
```

---

# Common Configuration Mistakes

---

## Missing Application Name

Wrong:

```yaml
spring:
  application:
```

No name provided.

---

Correct:

```yaml
spring:
  application:
    name: product-service
```

---

## Wrong Eureka URL

Wrong:

```yaml
defaultZone:
  http://localhost:9999/eureka/
```

Connection fails.

---

Correct:

```yaml
defaultZone:
  http://localhost:8761/eureka/
```

---

## Eureka Server Not Running

Client startup succeeds.

Registration fails.

Dashboard remains empty.

---

## Port Conflicts

Wrong:

```text
Product Service -> 8081

Order Service -> 8081
```

Only one service can use the port.

---

Use unique ports.

---

# Why Registration Is Important

Without registration:

```text
Service exists
```

but nobody can find it.

---

With registration:

```text
Service exists

Service is discoverable
```

Other services can communicate automatically.

---

This is the foundation of:

- Service Discovery
- Load Balancing
- Feign Clients
- API Gateway Routing

---

# Key Takeaways

- A Eureka Client is any application that communicates with Eureka Server.
- Services register themselves automatically during startup.
- Registration requires the Eureka Client dependency.
- `spring.application.name` becomes the service identifier.
- `defaultZone` specifies the Eureka Server location.
- Registered services appear in Eureka Dashboard.
- Heartbeats maintain service health.
- Lease renewal keeps instances active.
- Failed instances are automatically removed.
- Registration enables service discovery throughout the system.

---

# Interview Questions

## Q1. What is a Eureka Client?

A service that registers with Eureka Server, discovers other services, and sends periodic heartbeats.

---

## Q2. Which dependency enables Eureka Client functionality?

```xml
spring-cloud-starter-netflix-eureka-client
```

---

## Q3. Why is spring.application.name important?

It becomes the unique service identifier used during registration and discovery.

---

## Q4. What is defaultZone?

The URL of the Eureka Server that the client connects to.

---

## Q5. What information is registered with Eureka?

- Service Name
- Host/IP
- Port
- Status
- Metadata

---

## Q6. What is a heartbeat?

A periodic lease renewal request sent to Eureka indicating that the service is healthy.

---

## Q7. What happens when heartbeats stop?

The lease expires and Eureka removes the service instance from the registry.

---

# Next Topic

In the next document:

**11-eureka-dashboard.md**

Topics:

- Exploring the Eureka Dashboard
- Understanding Registered Applications
- Service Instance Information
- Health Status
- Registry Details
- Monitoring Services Through Eureka
- Troubleshooting Registration Issues