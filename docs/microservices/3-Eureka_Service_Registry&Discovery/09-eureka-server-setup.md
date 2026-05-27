# Creating and Configuring Eureka Server

## Learning Objectives

After completing this document, you should be able to:

- Create a Eureka Server from scratch
- Understand required Maven dependencies
- Configure Eureka Server using Spring Boot
- Configure application.yml properly
- Start and verify Eureka Server
- Understand Eureka Dashboard
- Verify service registrations
- Explain Eureka Server setup in interviews

---

# Introduction

In the previous chapter, we learned Eureka Architecture.

We learned:

- Eureka Server acts as a Service Registry
- Services register themselves with Eureka
- Services discover each other through Eureka
- Heartbeats maintain service health
- Eureka stores registry information

Now it is time to build our first Eureka Server.

---

# What Are We Building?

We are creating a dedicated Eureka Server application.

Example Architecture:

```text
                 Eureka Server
                        |
      ---------------------------------
      |              |               |
 Product Service  Order Service  Payment Service
```

All services will:

- Register with Eureka
- Send heartbeats
- Discover other services

---

# Prerequisites

Before creating Eureka Server, ensure you have:

- Java 17+ (or your project version)
- Maven
- Spring Boot knowledge
- IDE (IntelliJ / Eclipse / VS Code)

---

# Step 1: Create Spring Boot Project

Create a new Spring Boot project.

Project Name:

```text
eureka-server
```

Example:

```text
com.example.eurekaserver
```

---

# Step 2: Required Dependency

Open Spring Initializr.

Add dependency:

```text
Eureka Server
```

---

Equivalent Maven dependency:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

---

# Spring Cloud Dependency Management

Spring Cloud versions must match Spring Boot versions.

Therefore Spring Cloud BOM (Bill Of Materials) is used.

Example:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

---

# Sample pom.xml

Example:

```xml
<properties>
    <java.version>17</java.version>
    <spring-cloud.version>2023.0.3</spring-cloud.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>

</dependencies>
```

---

# Project Structure

Basic project structure:

```text
eureka-server
|
|-- src/main/java
|    |
|    |-- EurekaServerApplication.java
|
|-- src/main/resources
|    |
|    |-- application.yml
|
|-- pom.xml
```

---

# Step 3: Enable Eureka Server

Create main class.

Example:

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            EurekaServerApplication.class,
            args
        );
    }
}
```

---

# Understanding @EnableEurekaServer

This annotation transforms a normal Spring Boot application into a Eureka Server.

Without it:

```text
Regular Spring Boot Application
```

With it:

```text
Service Registry
```

---

Responsibilities enabled:

- Service Registration
- Registry Management
- Heartbeat Tracking
- Service Discovery APIs
- Eureka Dashboard

---

# Step 4: Configure application.yml

Create:

```text
application.yml
```

---

Basic configuration:

```yaml
server:
  port: 8761

spring:
  application:
    name: eureka-server

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

---

# Understanding server.port

```yaml
server:
  port: 8761
```

Default Eureka convention:

```text
8761
```

You can use any port.

Example:

```text
8080
9000
8761
```

---

Most examples use:

```text
8761
```

because it is widely recognized.

---

# Understanding Application Name

```yaml
spring:
  application:
    name: eureka-server
```

This identifies the application.

Example:

```text
eureka-server
```

---

Later client services use names like:

```text
product-service

order-service

payment-service
```

---

# Understanding register-with-eureka

Configuration:

```yaml
register-with-eureka: false
```

Meaning:

```text
Do NOT register yourself
```

---

Why?

Because this application IS the Eureka Server.

There is no other registry to register with.

---

If enabled:

```yaml
register-with-eureka: true
```

Server would try registering itself.

Not needed for a standalone Eureka Server.

---

# Understanding fetch-registry

Configuration:

```yaml
fetch-registry: false
```

Meaning:

```text
Do NOT download registry information
```

---

Why?

The Eureka Server maintains the registry.

It does not need to fetch it from somewhere else.

---

Therefore:

```yaml
register-with-eureka: false

fetch-registry: false
```

are standard settings.

---

# Complete application.yml

```yaml
server:
  port: 8761

spring:
  application:
    name: eureka-server

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

---

# Step 5: Start Eureka Server

Run application:

```bash
mvn spring-boot:run
```

or

Run from IDE.

---

Startup log:

```text
Started EurekaServerApplication
```

Server is now running.

---

# Step 6: Open Eureka Dashboard

Open browser:

```text
http://localhost:8761
```

---

Expected dashboard:

```text
Eureka Dashboard
```

---

You should see:

```text
System Status

Instances currently registered with Eureka
```

---

Initially:

```text
No instances available
```

This is expected.

No services are registered yet.

---

# Understanding Eureka Dashboard

The dashboard provides:

- Registered Services
- Instance Details
- Server Information
- Health Information

---

Example:

```text
Applications

PRODUCT-SERVICE

ORDER-SERVICE

PAYMENT-SERVICE
```

---

As services register, they appear here.

---

# Dashboard Sections

## System Status

Shows server state.

Example:

```text
UP
```

Meaning Eureka is running correctly.

---

## Instances Registered

Shows active services.

Example:

```text
PRODUCT-SERVICE

ORDER-SERVICE
```

---

## General Information

Contains:

- Server Version
- Environment
- Registry Statistics

---

# What Happens Internally?

When Eureka starts:

---

Step 1

Server initializes registry.

---

Step 2

Starts HTTP endpoints.

---

Step 3

Starts dashboard.

---

Step 4

Waits for client registrations.

---

Flow:

```text
Start Server
      |
Initialize Registry
      |
Expose Endpoints
      |
Start Dashboard
      |
Wait For Clients
```

---

# Eureka Endpoints

Eureka exposes REST endpoints internally.

Example:

```text
/eureka/apps
```

Provides application information.

---

Example:

```text
/eureka/apps/PRODUCT-SERVICE
```

Provides service details.

---

Clients use these APIs automatically.

Normally developers do not call them manually.

---

# Example Registration Flow

Suppose Product Service starts.

---

Product Service:

```text
localhost:8081
```

---

Registers with Eureka:

```text
PRODUCT-SERVICE
localhost:8081
```

---

Eureka Registry:

```text
PRODUCT-SERVICE

localhost:8081
```

---

Dashboard updates automatically.

---

# Common Configuration Mistakes

---

## Wrong Port

Incorrect:

```yaml
server:
  port:
```

No value provided.

Application fails.

---

Correct:

```yaml
server:
  port: 8761
```

---

## Missing @EnableEurekaServer

Without:

```java
@EnableEurekaServer
```

Application becomes normal Spring Boot app.

No registry functionality.

---

## Missing Dependency

Without:

```xml
spring-cloud-starter-netflix-eureka-server
```

Compilation fails.

---

## Version Mismatch

Spring Boot and Spring Cloud versions must be compatible.

Always verify compatibility matrix.

---

# Eureka Server Responsibilities

Once running, Eureka performs:

---

## Service Registration

Stores instance information.

---

## Service Discovery

Provides instance lookup.

---

## Health Tracking

Monitors heartbeats.

---

## Lease Management

Tracks renewals and expirations.

---

## Registry Maintenance

Updates active instance list.

---

# Why Eureka Server Is Important

Without Eureka:

```text
Hardcoded URLs
Manual Configuration
Difficult Scaling
Frequent Failures
```

---

With Eureka:

```text
Automatic Discovery
Dynamic Scaling
Service Registry
Health Monitoring
```

---

Microservices become significantly easier to manage.

---

# Key Takeaways

- Eureka Server acts as the central Service Registry.
- Spring Cloud Netflix Eureka provides the server implementation.
- `@EnableEurekaServer` enables registry functionality.
- Standard Eureka port is `8761`.
- `register-with-eureka=false` prevents self-registration.
- `fetch-registry=false` prevents registry fetching.
- Dashboard is available at `http://localhost:8761`.
- Services register automatically after startup.
- Eureka manages registration, discovery, and health monitoring.

---

# Interview Questions

## Q1. What is Eureka Server?

Eureka Server is a Service Registry that stores information about active microservice instances and allows service discovery.

---

## Q2. Why do we use @EnableEurekaServer?

It enables Eureka Server functionality within a Spring Boot application.

---

## Q3. Why is register-with-eureka set to false?

Because the Eureka Server itself does not need to register with another Eureka Server in a standalone setup.

---

## Q4. Why is fetch-registry set to false?

Because Eureka Server maintains the registry and does not need to download it.

---

## Q5. What is the default Eureka Server port?

8761.

---

## Q6. How do you access Eureka Dashboard?

```text
http://localhost:8761
```

---

## Q7. What information does Eureka Server store?

- Service Name
- Host/IP
- Port
- Status
- Metadata
- Lease Information

---

# Next Topic

In the next document:

**10-eureka-client-registration.md**

Topics:

- Creating a Eureka Client
- Registering Microservices with Eureka
- Client Configuration
- Heartbeats and Lease Renewal
- Service Registration Flow
- Verifying Registration in Eureka Dashboard