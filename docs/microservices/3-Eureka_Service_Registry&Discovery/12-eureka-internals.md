# Eureka Internals

## Learning Objectives

After completing this document, you should be able to:

- Understand how Eureka works internally
- Explain lease renewal and heartbeat mechanisms
- Understand registry synchronization
- Explain client-side caching
- Understand registry fetching
- Explain instance eviction
- Understand self-preservation mode deeply
- Explain Eureka internals confidently in interviews

---

# Introduction

So far we have learned:

- What Service Discovery is
- Why Eureka is needed
- Eureka Architecture
- Eureka Server Setup
- Eureka Client Registration
- Eureka Dashboard

At a high level, we know:

```text
Service Starts
      |
Registers with Eureka
      |
Sends Heartbeats
      |
Other Services Discover It
```

But what actually happens behind the scenes?

How does Eureka know a service is alive?

How are failed services removed?

How do clients know about newly registered services?

This chapter answers those questions.

---

# High-Level Eureka Workflow

Whenever a service starts:

```text
Start Service
     |
Register with Eureka
     |
Fetch Registry
     |
Cache Registry
     |
Send Heartbeats
     |
Discover Other Services
```

This entire cycle repeats continuously.

---

# Registration Deep Dive

When a service starts:

Example:

```text
PRODUCT-SERVICE
localhost:8081
```

It sends a registration request to Eureka.

---

Registration contains:

```text
Service Name

Host

IP Address

Port

Status

Metadata
```

---

Example:

```text
PRODUCT-SERVICE

127.0.0.1

8081

UP
```

---

Eureka stores this information in its registry.

---

# What is the Registry?

The Registry is Eureka's internal directory.

Think of it as a database containing:

```text
All Active Services
```

---

Example Registry:

```text
PRODUCT-SERVICE

localhost:8081

-------------------

ORDER-SERVICE

localhost:8082

-------------------

PAYMENT-SERVICE

localhost:8083
```

---

Whenever services register:

Registry updates automatically.

---

# Registry Data Structure (Conceptual)

Internally Eureka stores information similar to:

```text
Service Name
   |
   +---- Instance 1
   |
   +---- Instance 2
   |
   +---- Instance 3
```

---

Example:

```text
PRODUCT-SERVICE
     |
     +---- localhost:8081
     |
     +---- localhost:8082
```

---

This structure enables:

- Discovery
- Load Balancing
- Fault Tolerance

---

# Lease Concept

One of Eureka's most important concepts is:

# Lease

---

## What is a Lease?

A lease is a contract between:

```text
Service
     |
     |
Eureka Server
```

---

Meaning:

```text
I am alive.
Keep me in the registry.
```

---

As long as lease renewals continue:

Instance remains active.

---

If lease expires:

Instance is removed.

---

# Lease Duration

Default lease duration:

```text
90 seconds
```

Meaning:

If no renewal occurs for 90 seconds:

Instance may be removed.

---

Example:

```text
Last Renewal:
12:00:00

Lease Duration:
90 seconds

Expiration:
12:01:30
```

---

If no heartbeat arrives:

Lease expires.

---

# Heartbeats

A heartbeat is a lease renewal request.

Meaning:

```text
I am still alive.
```

---

Example:

```text
PRODUCT-SERVICE
      |
Heartbeat
      |
Eureka
```

---

Heartbeats are sent automatically.

No developer code required.

---

# Heartbeat Interval

Default heartbeat interval:

```text
30 seconds
```

---

Example:

```text
12:00:00

Heartbeat

12:00:30

Heartbeat

12:01:00

Heartbeat

12:01:30

Heartbeat
```

---

Each heartbeat renews the lease.

---

# Why Heartbeats Matter

Without heartbeats:

Eureka would never know if a service crashed.

---

Example:

```text
PRODUCT-SERVICE

Status: UP
```

---

Machine crashes.

---

Without heartbeats:

Registry would still contain:

```text
PRODUCT-SERVICE
```

even though it is dead.

---

This would cause discovery failures.

---

Heartbeats solve this problem.

---

# Lease Renewal Process

Let's examine the complete flow.

---

Step 1

Service starts.

---

Step 2

Registers itself.

---

Step 3

Lease created.

---

Step 4

Heartbeat sent every 30 seconds.

---

Step 5

Lease expiration timer reset.

---

Cycle repeats forever.

---

Flow:

```text
Register
   |
Create Lease
   |
Heartbeat
   |
Renew Lease
   |
Heartbeat
   |
Renew Lease
```

---

# What Happens If Heartbeats Stop?

Suppose:

```text
PRODUCT-SERVICE
```

crashes unexpectedly.

---

Before Crash:

```text
Heartbeat Received
```

---

After Crash:

```text
No Heartbeat
```

---

Time passes.

---

Lease expires.

---

Eureka marks instance for removal.

---

Registry before:

```text
PRODUCT-SERVICE

localhost:8081
```

---

Registry after:

```text
(no instance)
```

---

Clients no longer discover it.

---

# Eviction Process

Removing expired instances is called:

# Eviction

---

## What is Eviction?

Eviction is the process of removing expired service instances from the registry.

---

Example:

Before:

```text
PRODUCT-SERVICE

localhost:8081
```

---

Lease expires.

---

Eviction occurs.

---

After:

```text
PRODUCT-SERVICE

(no instances)
```

---

# Eviction Task

Eureka periodically checks:

```text
Which leases expired?
```

---

Expired instances are removed.

---

Example:

```text
Instance A -> Active

Instance B -> Active

Instance C -> Expired
```

---

Result:

```text
Instance C Removed
```

---

# Registry Fetching

Registration is only one part of discovery.

Clients also need information about other services.

---

Example:

```text
Order Service
```

needs:

```text
Inventory Service

Payment Service

User Service
```

---

How does it know they exist?

---

Answer:

Registry Fetching.

---

# Registry Fetch Mechanism

Clients periodically download registry information.

---

Example:

Eureka Registry:

```text
PRODUCT-SERVICE

ORDER-SERVICE

PAYMENT-SERVICE

USER-SERVICE
```

---

Order Service downloads:

```text
PRODUCT-SERVICE

PAYMENT-SERVICE

USER-SERVICE
```

---

Now it can discover services locally.

---

# Client-Side Cache

Downloaded registry information is stored locally.

This is called:

# Client Cache

---

Example:

```text
Order Service Cache

PRODUCT-SERVICE

PAYMENT-SERVICE

USER-SERVICE
```

---

Most lookups occur from cache.

---

Benefits:

- Faster discovery
- Reduced network calls
- Better performance
- Reduced load on Eureka

---

# Why Not Query Eureka Every Time?

Imagine:

```text
100 Services
```

Each making:

```text
1000 Requests/Second
```

---

If every lookup hit Eureka:

Huge load.

---

Performance would suffer.

---

Caching prevents this.

---

Flow:

```text
Order Service
      |
Local Cache
      |
Inventory Service
```

---

No Eureka call required.

---

# Cache Refresh

Registry changes continuously.

New services appear.

Old services disappear.

---

Therefore clients periodically refresh cache.

---

Example:

```text
Every 30 Seconds
```

Registry refreshed.

---

Latest information downloaded.

---

# Discovery Flow Internally

Suppose:

```text
Order Service
```

needs:

```text
Inventory Service
```

---

Step 1

Check local cache.

---

Step 2

Find instances:

```text
localhost:8081

localhost:8082
```

---

Step 3

Load Balancer chooses one.

---

Step 4

Request sent.

---

Flow:

```text
Order Service
      |
Cache Lookup
      |
Instance Selection
      |
Inventory Service
```

---

Eureka is not contacted for every request.

---

# Self-Preservation Mode

One of Eureka's most important features.

---

# The Problem

Imagine:

```text
100 Services
```

registered.

---

Suddenly:

Network issue occurs.

---

Heartbeats stop arriving.

---

Eureka sees:

```text
No Renewals
```

---

Without protection:

Eureka might think:

```text
All Services Died
```

---

It would remove every instance.

---

Catastrophic outcome.

---

# The Solution

Self-Preservation Mode.

---

## What is Self-Preservation?

A protection mechanism that prevents mass eviction during network failures.

---

Instead of immediately removing services:

Eureka becomes cautious.

---

Example:

Expected renewals:

```text
1000 renewals/minute
```

---

Actual renewals:

```text
150 renewals/minute
```

---

Large drop detected.

---

Eureka assumes:

```text
Network Problem
```

instead of:

```text
Massive Service Failure
```

---

# Behavior During Self-Preservation

Eureka:

```text
Stops aggressive eviction
```

---

Registry remains intact.

---

Services stay discoverable.

---

Once network recovers:

Normal operation resumes.

---

# Benefits of Self-Preservation

---

## Prevents Accidental Mass Removal

Avoids wiping entire registry.

---

## Handles Temporary Network Failures

Short outages become less dangerous.

---

## Improves Availability

Services remain discoverable longer.

---

## Increases Resilience

Distributed systems become more fault tolerant.

---

# Complete Internal Lifecycle

Let's combine everything.

---

Step 1

Service starts.

---

Step 2

Registers with Eureka.

---

Step 3

Lease created.

---

Step 4

Registry updated.

---

Step 5

Clients fetch registry.

---

Step 6

Clients cache registry.

---

Step 7

Heartbeats renew lease.

---

Step 8

Discovery occurs through cache.

---

Step 9

If heartbeats stop:

Lease expires.

---

Step 10

Eviction removes instance.

---

Lifecycle:

```text
Start
  |
Register
  |
Create Lease
  |
Update Registry
  |
Fetch Registry
  |
Cache Registry
  |
Heartbeats
  |
Discovery
  |
Lease Expiry
  |
Eviction
```

---

# Eureka Internal Architecture Summary

```text
                Eureka Server
                       |
        -----------------------------
        |            Registry        |
        -----------------------------
              ^               ^
              |               |
         Registration     Registry Fetch
              |               |
      -------------------------------
      |             |              |
 Product       Order         Payment
 Service       Service       Service
      |
 Heartbeats
      |
 Lease Renewal
```

---

# Key Takeaways

- Eureka maintains a registry of active service instances.
- Services register themselves during startup.
- Registration creates a lease.
- Heartbeats renew the lease periodically.
- Default heartbeat interval is approximately 30 seconds.
- Default lease expiration is approximately 90 seconds.
- Expired instances are removed through eviction.
- Clients download and cache registry information.
- Most service discovery occurs through local cache.
- Self-preservation mode prevents accidental mass eviction during network failures.

---

# Interview Questions

## Q1. What is a lease in Eureka?

A lease is a contract between a service instance and Eureka Server indicating that the service is alive.

---

## Q2. What is a heartbeat?

A periodic lease renewal request sent by a service to Eureka Server.

---

## Q3. What happens if heartbeats stop?

The lease expires and the instance may be evicted from the registry.

---

## Q4. What is eviction?

The process of removing expired service instances from Eureka's registry.

---

## Q5. Why do Eureka Clients maintain a local cache?

To reduce network calls, improve performance, and avoid querying Eureka for every service lookup.

---

## Q6. What is Self-Preservation Mode?

A protection mechanism that prevents Eureka from removing large numbers of services during temporary network failures.

---

## Q7. Does Eureka participate in every service request?

No. Eureka provides discovery information. Actual service requests typically use locally cached registry data.

---

# Next Topic

In the next document:

**13-inter-service-communication.md**

Topics:

- Why Services Need Communication
- Direct HTTP Calls
- Problems with Hardcoded URLs
- RestTemplate Overview
- WebClient Overview
- Challenges of Traditional Service Communication
- Why OpenFeign Was Introduced