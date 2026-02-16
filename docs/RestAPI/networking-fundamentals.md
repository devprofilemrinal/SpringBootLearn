# 🌐 REST & Networking Fundamentals — Complete Guide

---

# 1️⃣ HTTP Methods (GET, POST, PUT, DELETE, PATCH)

A **resource** is something addressable:

```
/users/42
/orders/100
/products/9
```

HTTP methods define what action we perform on that resource.

---

## 🔹 GET — Read

**Purpose:** Retrieve data  
**Safe:** Yes  
**Idempotent:** Yes

Example:
```
GET /users/42
```

GET does not modify server state.

Analogy:
Reading a book in a library. You observe it but do not change it.

---

## 🔹 POST — Create

**Purpose:** Create new resource  
**Safe:** No  
**Idempotent:** No

Example:
```
POST /users
```

Body:
```json
{
  "name": "Alice"
}
```

Each POST may create a new resource.

Analogy:
Submitting a form. Submitting twice may create duplicates.

---

## 🔹 PUT — Replace Entire Resource

**Purpose:** Replace full resource  
**Safe:** No  
**Idempotent:** Yes

Example:
```
PUT /users/42
```

Body:
```json
{
  "name": "Bob"
}
```

PUT replaces the entire resource.

Idempotent means:
```
f(f(x)) = f(x)
```

Sending same PUT multiple times results in same state.

Analogy:
Replacing a file with a new version. Replacing again with same file changes nothing further.

---

## 🔹 PATCH — Partial Update

**Purpose:** Modify part of resource  
**Safe:** No  
**Idempotent:** Usually No

Example:
```
PATCH /users/42
```

Body:
```json
{
  "email": "new@mail.com"
}
```

PATCH updates only specified fields.

Analogy:
Editing a paragraph in a document.

---

## 🔹 DELETE — Remove Resource

**Purpose:** Remove resource  
**Safe:** No  
**Idempotent:** Yes

Example:
```
DELETE /users/42
```

Deleting multiple times results in same final state (resource absent).

Analogy:
Throwing a document away. Once gone, deleting again does nothing.

---

# HTTP Methods Summary

| Method | Purpose | Safe | Idempotent |
|--------|---------|------|------------|
| GET    | Read    | Yes  | Yes        |
| POST   | Create  | No   | No         |
| PUT    | Replace | No   | Yes        |
| PATCH  | Partial Update | No | No |
| DELETE | Remove  | No   | Yes |

---

# 2️⃣ HTTP Status Codes

Status codes indicate the result of a request.

## Categories

| Range | Meaning |
|-------|---------|
| 1xx | Informational |
| 2xx | Success |
| 3xx | Redirection |
| 4xx | Client Error |
| 5xx | Server Error |

---

## Common Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK |
| 201 | Created |
| 204 | No Content |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 409 | Conflict |
| 500 | Internal Server Error |

Returning `200` for every response removes semantic meaning and makes error handling difficult.

---

# 3️⃣ HTTP vs HTTPS

## HTTP
- HyperText Transfer Protocol
- Data sent in plain text
- No encryption
- Vulnerable to interception

## HTTPS
- HTTP over TLS
- Encrypted communication
- Provides:
    - Confidentiality
    - Integrity
    - Authentication

Analogy:

HTTP is like sending a postcard.  
HTTPS is like sending a sealed, tamper-proof envelope.

---

# 4️⃣ TCP vs UDP

Transport layer protocols.

---

## 🔹 TCP (Transmission Control Protocol)

- Reliable
- Connection-oriented
- Guarantees delivery
- Guarantees order
- Error checking
- Slower due to overhead

Analogy:
Registered courier delivery with tracking and confirmation.

Used by:
- HTTP
- HTTPS
- Email
- File transfer

---

## 🔹 UDP (User Datagram Protocol)

- Unreliable
- Connectionless
- No delivery guarantee
- No order guarantee
- Faster
- Minimal overhead

Analogy:
Shouting a message in a crowd — fast but no guarantee everyone hears it.

Used by:
- Streaming
- Online gaming
- DNS
- VoIP

---

## TCP vs UDP Comparison

| Feature | TCP | UDP |
|----------|-----|-----|
| Reliable | Yes | No |
| Ordered | Yes | No |
| Speed | Slower | Faster |
| Connection Required | Yes | No |
| Use Case | Web, Email | Streaming, Gaming |

---

# 5️⃣ SSL vs TLS

Both are encryption protocols.

## SSL (Secure Sockets Layer)
- Older protocol
- Deprecated
- Known vulnerabilities

## TLS (Transport Layer Security)
- Successor to SSL
- More secure
- Used in HTTPS today

When people say “SSL certificate”, they usually mean TLS certificate.

---

# TLS Handshake (Simplified Steps)

1. Client sends “Hello”
2. Server sends certificate
3. Client verifies certificate
4. Secure key exchange occurs
5. Encrypted session begins

---

# Layer Model Overview

```
Application Layer → HTTP / HTTPS
Transport Layer   → TCP / UDP
Security Layer    → TLS (for HTTPS)
```

---
