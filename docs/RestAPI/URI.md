# 🌍 URI & Naming Conventions in REST APIs

To design clean REST APIs, you must understand **URI structure** and **naming discipline**.

A badly designed URI is like a badly labeled street in a city — confusing, hard to scale, and difficult to navigate.

A well-designed URI is like a well-planned city grid — predictable, extensible, and intuitive.

---

# 1️⃣ What Is a URI?

**URI = Uniform Resource Identifier**

It identifies a resource.

General structure:

```
scheme://host:port/path?query#fragment
```

Example:

```
https://api.example.com/users/42/orders?page=2
```

Breakdown:

- `https` → scheme
- `api.example.com` → host
- `/users/42/orders` → path
- `?page=2` → query parameters

---

# URI vs URL vs URN

- **URI** = Identifier (general concept)
- **URL** = Locator (tells where resource lives)
- **URN** = Name (persistent identifier)

In REST APIs, we mostly use URLs.

---

# 2️⃣ URI as Resource Identity

In REST:

> URI identifies a resource — not an action.

Think of a URI like a street address.

```
/users/42
```

This identifies:
- A specific user
- With ID 42

It does NOT describe what to do with it.

The HTTP method defines the action:
- GET → read
- PUT → replace
- DELETE → remove

---

# 3️⃣ Core Naming Principles

## 🔹 1. Use Nouns, Not Verbs

❌ Bad:
```
/createUser
/getUser
/deleteUser
```

✅ Good:
```
POST   /users
GET    /users/42
DELETE /users/42
```

Why?

Because:
- URIs identify resources
- HTTP verbs define actions

This separation reduces coupling and improves scalability.

---

## 🔹 2. Use Plural Resource Names

✅ Good:
```
/users
/products
/orders
```

Why plural?

Because the URI represents a collection.

Then:
```
/users/42
```

Represents one element inside that collection.

---

## 🔹 3. Use Hierarchy for Relationships

Example:

```
/users/42/orders
```

This means:
Orders that belong to user 42.

But avoid excessive nesting:

❌ Bad:
```
/users/42/orders/9/items/4/payments/2
```

Deep nesting creates:
- Long URLs
- Tight coupling
- Hard-to-maintain endpoints

Better:
```
/payments/2
```

Keep URIs clean and stable.

---

## 🔹 4. Use Hyphens, Not Underscores

✅ Good:
```
/user-profiles
/order-history
```

❌ Avoid:
```
/user_profiles
/orderHistory
```

Why?

- Hyphens improve readability
- More consistent in URLs
- Better for SEO (for public APIs)

---

## 🔹 5. Use Lowercase Only

URIs are case-sensitive in many systems.

❌ Bad:
```
/Users/42
```

✅ Good:
```
/users/42
```

Lowercase prevents subtle bugs.

---

## 🔹 6. Use Query Parameters for Filtering

Filtering:
```
GET /users?role=admin
```

Pagination:
```
GET /users?page=2&limit=20
```

Sorting:
```
GET /users?sort=name
```

Use query parameters for:
- Optional filtering
- Searching
- Sorting
- Pagination

NOT for resource identity.

---

# 4️⃣ Resource vs Action Thinking

Wrong mental model:
> API is a list of functions.

Correct model:
> API is a collection of resources.

Think in terms of objects in a database, not procedures.

Bad (RPC style):
```
/approveOrder
/cancelOrder
/processPayment
```

Better:
```
POST /orders/42/approval
DELETE /orders/42
POST /payments
```

Or even better:
Model state transitions properly.

---

# 5️⃣ Versioning in URIs

Common pattern:

```
/v1/users
```

Pros:
- Simple
- Explicit

Cons:
- Breaks purity of resource identity

Alternative:
Use headers for versioning.

But in practice, URI versioning is common and acceptable.

---

# 6️⃣ Consistency Rules

Choose conventions and stick to them:

- Always plural
- Always lowercase
- Always hyphen-separated
- Always resource-oriented

Inconsistency is worse than any single design decision.

---

# 7️⃣ Good URI Examples

```
GET    /users
GET    /users/42
GET    /users/42/orders
POST   /users
PUT    /users/42
PATCH  /users/42
DELETE /users/42
```

Filtering and paging:

```
GET /orders?status=completed&page=2&limit=50
```

---

# 8️⃣ Anti-Patterns to Avoid

❌ Verbs in URIs  
❌ Deep nesting  
❌ Mixing singular and plural  
❌ CamelCase  
❌ Encoding actions into path  
❌ Returning everything in one endpoint

---

# 9️⃣ Mental Model Summary

Think of URI design like city planning:

- Each resource = building
- Collection = neighborhood
- HTTP methods = legal operations
- Query params = search filters

Clean planning allows:
- Easy navigation
- Predictability
- Scalability
- Independent evolution

--