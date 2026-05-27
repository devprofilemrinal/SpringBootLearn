# Microservice Communication Patterns

## Learning Objectives

After completing this document, you should be able to:

- Understand why microservices need to communicate
- Differentiate between synchronous and asynchronous communication
- Understand REST-based communication
- Understand messaging and event-driven communication
- Identify common communication patterns used in microservices
- Select the appropriate communication style for a given use case
- Explain communication patterns in interviews

---

# Introduction

In a Monolithic Architecture, modules communicate through direct method calls.

Example:

```java
paymentService.processPayment();
inventoryService.updateStock();
```

Since all modules run inside the same application process, communication is simple and fast.

---

In a Microservices Architecture, each service runs independently.

Example:

```text
User Service
Product Service
Inventory Service
Order Service
Payment Service
Notification Service
```

Since services are separate applications, they must communicate over a network.

This introduces new challenges:

- Network latency
- Service failures
- Timeouts
- Retry mechanisms
- Message delivery guarantees

Understanding communication patterns is therefore one of the most important aspects of Microservices Architecture.

---

# Why Do Microservices Need Communication?

A service usually cannot complete a business operation alone.

Consider an order placement process.

Customer places an order.

The Order Service must:

1. Verify user information
2. Check inventory availability
3. Process payment
4. Generate order
5. Send notification

Multiple services participate in one business flow.

Example:

```text
Customer
    |
Order Service
    |
    +--> User Service
    |
    +--> Inventory Service
    |
    +--> Payment Service
    |
    +--> Notification Service
```

Communication between services becomes mandatory.

---

# Types of Communication

Microservice communication is broadly divided into:

1. Synchronous Communication
2. Asynchronous Communication

---

# Synchronous Communication

## Definition

In synchronous communication:

The caller sends a request and waits for a response.

The calling service cannot proceed until it receives a response.

---

## Real-Life Example

Imagine calling customer support.

You ask a question.

You wait on the phone until they answer.

This is synchronous communication.

---

## Example in Microservices

```text
Order Service
      |
      | Request
      V
Inventory Service
      |
      | Response
      V
Order Service
```

Order Service waits until Inventory Service responds.

---

## REST Example

Order Service calls:

```http
GET /inventory/check/101
```

Inventory Service responds:

```json
{
  "available": true
}
```

Only after receiving the response does Order Service continue processing.

---

## Advantages

### Simple to Understand

Request-response model is intuitive.

---

### Immediate Result

Caller receives an instant response.

---

### Easy Error Handling

Failures are immediately visible.

---

### Widely Supported

REST APIs and HTTP are universally supported.

---

## Disadvantages

### Tight Runtime Dependency

If Inventory Service is unavailable:

Order Service may fail.

---

### Increased Latency

Every service call adds network delay.

---

### Cascading Failures

Failure of one service may impact others.

Example:

```text
Inventory Down
      |
Order Fails
      |
Payment Fails
      |
User Request Fails
```

---

### Reduced Resilience

System becomes dependent on service availability.

---

# Common Synchronous Technologies

## REST APIs

Most commonly used approach.

Uses:

- HTTP
- JSON

Example:

```http
GET /products
POST /orders
PUT /inventory
```

---

## gRPC

High-performance communication framework.

Uses:

- HTTP/2
- Protocol Buffers

Benefits:

- Faster communication
- Smaller payloads

Often used in high-performance systems.

---

# Asynchronous Communication

## Definition

In asynchronous communication:

The sender sends a message and continues processing without waiting for a response.

The receiving service processes the message later.

---

## Real-Life Example

Sending an email.

You send the email and continue working.

You do not wait for the recipient to read it.

This is asynchronous communication.

---

## Example in Microservices

```text
Order Service
      |
      | Message
      V
Message Queue
      |
      V
Notification Service
```

Order Service continues immediately.

Notification Service processes the message later.

---

# Advantages

## Better Performance

No waiting for immediate responses.

---

## Improved Scalability

Consumers can process messages independently.

---

## Better Fault Tolerance

Messages remain in queue until processed.

---

## Loose Coupling

Services become less dependent on each other.

---

## Improved Reliability

Temporary failures can be handled later.

---

# Disadvantages

## Increased Complexity

Messaging infrastructure must be managed.

---

## Eventual Consistency

Data may not be immediately synchronized.

---

## More Difficult Debugging

Tracing message flows becomes harder.

---

## Message Ordering Challenges

Multiple consumers may process messages differently.

---

# Message Broker

Asynchronous systems typically use a Message Broker.

A broker acts as an intermediary between services.

Example:

```text
Producer
   |
   V
Message Broker
   |
   V
Consumer
```

---

# Common Message Brokers

## RabbitMQ

Popular message queue system.

Features:

- Reliable delivery
- Routing
- Retry support

---

## Apache Kafka

Distributed event-streaming platform.

Features:

- High throughput
- Event storage
- Real-time streaming

Used by:

- Netflix
- Uber
- LinkedIn

---

## Amazon SQS

Managed queue service provided by AWS.

---

# Communication Patterns

Several communication styles are commonly used in Microservices.

---

# Request-Response Pattern

Most common pattern.

A service sends a request and waits for a response.

Example:

```text
Order Service
      |
      V
Inventory Service
      |
      V
Response
```

Usually implemented using:

- REST
- gRPC

---

# Publish-Subscribe Pattern

Producer publishes events.

Multiple consumers receive them.

Example:

```text
Order Created Event
         |
         V
     Event Broker
     /         \
    /           \
Notification   Analytics
 Service        Service
```

One event can trigger multiple actions.

---

## Example

Order Created Event:

```json
{
  "orderId": 1001
}
```

Consumers:

- Notification Service sends email
- Analytics Service updates dashboard
- Loyalty Service awards points

All receive the same event.

---

# Event-Driven Architecture

A specialized form of asynchronous communication.

Services react to events rather than direct requests.

---

## Example

Customer places order.

Event emitted:

```text
OrderCreated
```

Consumers:

```text
Inventory Service
Payment Service
Notification Service
Shipping Service
```

Each reacts independently.

---

## Benefits

- Loose coupling
- High scalability
- Independent processing
- Better resilience

---

# One-Way Messaging

A service sends a message without expecting a response.

Example:

```text
Order Service
      |
      V
Notification Queue
```

No response required.

Suitable for:

- Emails
- SMS
- Logging
- Audit events

---

# Command Pattern

One service instructs another service to perform an action.

Example:

```text
Process Payment
Reserve Inventory
Generate Invoice
```

The message contains a command.

---

# Event Pattern

A service announces that something happened.

Example:

```text
PaymentCompleted
OrderCreated
UserRegistered
ShipmentDelivered
```

No specific action is requested.

Other services decide how to react.

---

# Communication Flow Example

Customer places an order.

---

## Step 1

Order Service receives request.

```text
Place Order
```

---

## Step 2

Order Service synchronously calls Inventory Service.

```text
Check Stock
```

Response:

```text
Available
```

---

## Step 3

Order Service synchronously calls Payment Service.

```text
Process Payment
```

Response:

```text
Success
```

---

## Step 4

Order Service creates order.

---

## Step 5

Order Service publishes event.

```text
OrderCreated
```

---

## Step 6

Notification Service consumes event.

Sends email.

---

## Step 7

Analytics Service consumes event.

Updates reports.

---

Final Architecture:

```text
Customer
   |
Order Service
   |
   +----> Inventory Service
   |
   +----> Payment Service
   |
   +----> Event Broker
                |
      --------------------
      |                  |
Notification       Analytics
 Service            Service
```

---

# Choosing the Right Communication Pattern

| Scenario | Recommended Pattern |
|-----------|---------------------|
| Need immediate response | Synchronous |
| Validation request | Synchronous |
| User login verification | Synchronous |
| Payment authorization | Synchronous |
| Email notifications | Asynchronous |
| SMS notifications | Asynchronous |
| Analytics processing | Asynchronous |
| Audit logging | Asynchronous |
| Large-scale event processing | Event Driven |
| Multiple consumers need same data | Publish-Subscribe |

---

# Best Practices

## Prefer Loose Coupling

Services should communicate through contracts rather than internal implementations.

---

## Avoid Excessive Synchronous Calls

Long chains increase latency and failure risk.

Bad Example:

```text
A -> B -> C -> D -> E
```

---

## Use Events for Side Effects

Examples:

- Notifications
- Reporting
- Auditing

---

## Implement Timeouts

Never wait indefinitely for another service.

---

## Implement Retries Carefully

Retries help with temporary failures but can create duplicate operations if misused.

---

## Monitor Communication

Track:

- Response times
- Failures
- Queue sizes
- Message delays

---

# Key Takeaways

- Microservices must communicate to complete business processes.
- Communication can be synchronous or asynchronous.
- Synchronous communication waits for immediate responses.
- REST and gRPC are common synchronous technologies.
- Asynchronous communication uses message brokers.
- RabbitMQ and Kafka are popular messaging platforms.
- Event-driven architecture improves scalability and loose coupling.
- Different communication patterns solve different business problems.
- Successful microservice systems often use both synchronous and asynchronous communication together.

---

# Interview Questions

## Q1. Why do microservices need communication?

Because business workflows often require multiple services to collaborate to complete a task.

---

## Q2. What is synchronous communication?

A communication style where the caller waits for a response before continuing execution.

---

## Q3. What is asynchronous communication?

A communication style where the sender sends a message and continues processing without waiting for a response.

---

## Q4. What are common synchronous communication technologies?

- REST APIs
- gRPC

---

## Q5. What are common message brokers?

- RabbitMQ
- Apache Kafka
- Amazon SQS

---

## Q6. What is Event-Driven Architecture?

An architecture where services communicate through events and react independently when events occur.

---

## Q7. What is the Publish-Subscribe pattern?

A messaging pattern where one event is published and multiple consumers receive and process it independently.

---

# Next Topic

In the next document:

**05-challenges-in-microservices.md**

Topics:

- Service Discovery Challenges
- Configuration Management
- Fault Tolerance
- Distributed Transactions
- Monitoring and Logging
- Security Challenges
- Observability
- Why Spring Cloud Exists