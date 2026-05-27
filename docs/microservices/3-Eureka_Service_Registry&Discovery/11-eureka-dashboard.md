# Understanding the Eureka Dashboard

## Learning Objectives

After completing this document, you should be able to:

- Navigate the Eureka Dashboard
- Understand registered applications and instances
- Interpret service status information
- Understand instance metadata
- Monitor registered services
- Verify service registration health
- Troubleshoot common registration problems
- Use the dashboard effectively during development

---

# Introduction

In the previous chapter, we learned how services register themselves with Eureka Server.

We learned:

- How Eureka Clients register
- How registration works
- Heartbeats and lease renewals
- Service lifecycle
- Registry updates

Now let's explore the Eureka Dashboard and understand how to monitor the microservices ecosystem visually.

The Eureka Dashboard is one of the most useful tools during development because it allows us to verify:

- Which services are registered
- Which instances are healthy
- Whether registration is working correctly
- Service availability

---

# What is the Eureka Dashboard?

## Definition

The Eureka Dashboard is a web interface provided by Eureka Server that displays information about registered services and their instances.

---

Think of it as:

```text
Control Panel
```

for Service Discovery.

---

It allows developers to:

- View registered services
- View instance information
- Verify health status
- Monitor service registration
- Troubleshoot discovery issues

---

# Accessing the Dashboard

After starting Eureka Server:

Open browser:

```text
http://localhost:8761
```

---

Default Port:

```text
8761
```

---

Example URL:

```text
http://localhost:8761
```

---

You should see the Eureka Dashboard home page.

---

# Dashboard Overview

The dashboard generally contains:

```text
System Status

General Information

Instances currently registered with Eureka
```

---

Example Layout:

```text
------------------------------------------------
| Eureka Dashboard                             |
------------------------------------------------

System Status: UP

Instances currently registered with Eureka

PRODUCT-SERVICE

ORDER-SERVICE

PAYMENT-SERVICE
```

---

# First Startup Scenario

Suppose only Eureka Server is running.

Dashboard displays:

```text
Instances currently registered with Eureka

No instances available
```

---

This is normal.

No services have registered yet.

---

# Registering a Service

Suppose Product Service starts.

Configuration:

```yaml
spring:
  application:
    name: product-service
```

---

Service registers with Eureka.

Dashboard updates automatically.

---

Dashboard:

```text
Instances currently registered with Eureka

PRODUCT-SERVICE
```

---

This confirms successful registration.

---

# Registered Applications Section

The most important section is:

```text
Instances currently registered with Eureka
```

---

This section lists:

- Registered applications
- Instance count
- Status

---

Example:

```text
PRODUCT-SERVICE

n/a (1)
```

---

Meaning:

```text
One active instance
```

exists.

---

# Understanding Service Names

Services appear using:

```text
spring.application.name
```

---

Configuration:

```yaml
spring:
  application:
    name: product-service
```

---

Dashboard displays:

```text
PRODUCT-SERVICE
```

---

Notice:

Eureka converts names to uppercase.

---

Example:

```text
order-service
```

becomes:

```text
ORDER-SERVICE
```

---

# Multiple Registered Services

Suppose several services register.

Dashboard:

```text
PRODUCT-SERVICE

ORDER-SERVICE

PAYMENT-SERVICE

USER-SERVICE
```

---

This indicates all services are discoverable.

---

# Multiple Instances of Same Service

Suppose Product Service scales.

Instances:

```text
localhost:8081

localhost:8082

localhost:8083
```

---

Dashboard:

```text
PRODUCT-SERVICE

n/a (3)
```

---

Meaning:

```text
Three active instances
```

are registered.

---

# Expanding Service Details

Clicking a service expands instance details.

Example:

```text
PRODUCT-SERVICE
```

---

Displays:

```text
Instance ID

Host Name

IP Address

Port

Status

Metadata
```

---

# Instance ID

## What is Instance ID?

A unique identifier for a service instance.

Example:

```text
PRODUCT-SERVICE:8081
```

or

```text
localhost:product-service:8081
```

---

Purpose:

Distinguishes multiple instances.

---

Example:

```text
PRODUCT-SERVICE:8081

PRODUCT-SERVICE:8082

PRODUCT-SERVICE:8083
```

Each instance has its own ID.

---

# Host Name

Shows machine name.

Example:

```text
localhost
```

or

```text
server-01
```

---

Used for identification.

---

# IP Address

Displays network address.

Example:

```text
127.0.0.1
```

or

```text
10.10.1.25
```

---

Useful when troubleshooting deployments.

---

# Port Number

Displays service port.

Example:

```text
8081
```

---

Helps verify:

- Service location
- Correct configuration
- Deployment environment

---

# Status Information

One of the most important fields.

---

Common statuses:

```text
UP
```

```text
DOWN
```

```text
STARTING
```

```text
OUT_OF_SERVICE
```

```text
UNKNOWN
```

---

# Status: UP

Meaning:

```text
Service is healthy
```

Heartbeats are being received.

Instance is available for discovery.

---

Example:

```text
PRODUCT-SERVICE

Status: UP
```

---

# Status: DOWN

Meaning:

```text
Service unavailable
```

Usually indicates:

- Service crash
- Failed health check
- Registration issue

---

# Status: STARTING

Meaning:

```text
Service startup in progress
```

Initialization not completed yet.

---

# Status: OUT_OF_SERVICE

Meaning:

```text
Temporarily unavailable
```

May be intentionally disabled.

---

# Status: UNKNOWN

Meaning:

```text
Health cannot be determined
```

Usually indicates communication issues.

---

# Metadata

Eureka allows custom metadata.

Example:

```text
Version

Region

Environment

Build Information
```

---

Example Metadata:

```text
version=1.0

environment=dev
```

---

Useful for:

- Deployment tracking
- Routing decisions
- Operational visibility

---

# Registry Information

Internally Eureka maintains a registry.

Dashboard reflects registry contents.

Example:

```text
PRODUCT-SERVICE

localhost:8081

Status: UP
```

means registry contains:

```text
PRODUCT-SERVICE

Instance:
localhost:8081
```

---

Dashboard is essentially a visual representation of the registry.

---

# Monitoring Heartbeats

Heartbeats keep services alive.

Each service periodically renews its lease.

Example:

```text
Every 30 Seconds
```

Heartbeat sent.

---

Dashboard continues displaying:

```text
Status: UP
```

---

If heartbeats stop:

Lease expires.

Instance eventually disappears.

---

# Example Registration Flow

Step 1

Start Eureka Server.

Dashboard:

```text
No instances available
```

---

Step 2

Start Product Service.

Dashboard:

```text
PRODUCT-SERVICE
```

---

Step 3

Start Order Service.

Dashboard:

```text
PRODUCT-SERVICE

ORDER-SERVICE
```

---

Step 4

Start Payment Service.

Dashboard:

```text
PRODUCT-SERVICE

ORDER-SERVICE

PAYMENT-SERVICE
```

---

System becomes fully discoverable.

---

# Detecting Registration Problems

Dashboard is extremely useful for troubleshooting.

---

## Problem 1

Service Not Visible

Dashboard:

```text
No instances available
```

---

Possible Causes:

- Eureka dependency missing
- Wrong Eureka URL
- Eureka Server not running
- Network issue

---

# Problem 2

Wrong Service Name

Expected:

```text
PRODUCT-SERVICE
```

Actual:

```text
UNKNOWN
```

---

Check:

```yaml
spring.application.name
```

---

# Problem 3

Incorrect Port

Dashboard:

```text
PRODUCT-SERVICE

Port: 8080
```

Expected:

```text
8081
```

---

Verify:

```yaml
server.port
```

---

# Problem 4

Service Keeps Disappearing

Possible causes:

- Heartbeat failure
- Network issue
- Application crashes
- Lease expiration

---

Check logs for errors.

---

# Monitoring During Development

During local development the dashboard helps verify:

---

## Service Startup

Did the service register successfully?

---

## Instance Count

Are all instances running?

---

## Discovery Configuration

Is service name correct?

---

## Port Verification

Is service running on expected port?

---

## Health Monitoring

Are heartbeats being received?

---

# Dashboard vs Actual Service Health

Important note:

Eureka Dashboard shows:

```text
Registration Health
```

not full application health.

---

Example:

Application may be:

```text
UP in Eureka
```

but

```text
Database connection failing
```

inside the service.

---

For detailed health checks use:

```text
Spring Boot Actuator
```

which we will discuss later.

---

# Common Dashboard Terminology

| Term | Meaning |
|--------|---------|
| Application | Registered service |
| Instance | Running copy of service |
| Registry | Internal service directory |
| Status | Health state |
| Lease | Registration validity period |
| Renewal | Heartbeat update |
| Metadata | Additional service information |
| Discovery | Service lookup process |

---

# Key Takeaways

- Eureka Dashboard is available at `http://localhost:8761`.
- It displays all registered services and instances.
- Service names come from `spring.application.name`.
- Multiple instances appear under the same application.
- Status information indicates service availability.
- Instance details include host, IP, port, and metadata.
- The dashboard is useful for monitoring registrations.
- Missing services often indicate registration problems.
- Dashboard reflects Eureka's internal registry.
- It is one of the most important troubleshooting tools during development.

---

# Interview Questions

## Q1. How do you access the Eureka Dashboard?

```text
http://localhost:8761
```

---

## Q2. What does the Eureka Dashboard show?

Registered applications, service instances, status information, host details, ports, and metadata.

---

## Q3. Where does Eureka get the service name from?

From:

```yaml
spring.application.name
```

---

## Q4. What does "UP" status mean?

The service is healthy and actively sending heartbeats.

---

## Q5. What does "n/a (3)" mean?

The application currently has three active registered instances.

---

## Q6. Why might a service not appear in the dashboard?

Possible reasons:

- Missing Eureka dependency
- Incorrect Eureka URL
- Eureka Server not running
- Registration failure

---

## Q7. Does Eureka Dashboard guarantee application health?

No. It primarily reflects registration and heartbeat status, not complete business functionality or dependency health.

---

# Next Topic

In the next document:

**12-eureka-internals.md**

Topics:

- Lease Renewal Process
- Heartbeats in Detail
- Registry Synchronization
- Client-Side Caching
- Registry Fetch Mechanism
- Instance Eviction
- Self-Preservation Mode Deep Dive
- Eureka Internal Architecture