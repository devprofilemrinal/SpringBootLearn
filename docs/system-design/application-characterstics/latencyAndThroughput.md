# 📘 Latency vs Throughput – Complete Notes

## 🧠 Definitions

### ⏱️ Latency
- Time taken to process a **single request**
- Measured in: milliseconds (ms), seconds

👉 Example: API response time = 100 ms

---

### 🚀 Throughput
- Number of requests processed **per unit time**
- Measured in: requests/sec (RPS), transactions/sec (TPS)

👉 Example: Server handles 10,000 requests/sec

---

## ⚡ Key Difference

| Aspect        | Latency                          | Throughput                     |
|--------------|----------------------------------|--------------------------------|
| Meaning      | Time per request                 | Work per unit time            |
| Focus        | Speed                            | Capacity                      |
| Unit         | ms / seconds                     | RPS / TPS                     |
| Goal         | Reduce delay                     | Increase volume               |

---

## 🔄 Relationship

- Latency and throughput are **independent but related**
- Improving one can **negatively impact** the other

👉 Example:
- Adding batching → increases throughput but also latency
- Adding parallelism → increases throughput, may increase latency due to contention

---

## 🔀 4 Possible Combinations

---

### ✅ 1. Low Latency + High Throughput (Ideal System)

**Meaning:**  
Fast response + handles many users

**Examples:**
- Google Search
- CDN systems (Cloudflare, Akamai)
- In-memory cache (Redis)

**Why it works:**
- Efficient algorithms
- Horizontal scaling
- Caching
- Load balancing

---

### 🐢 2. High Latency + Low Throughput (Worst Case)

**Meaning:**  
Slow response + cannot handle load

**Examples:**
- Poorly designed monolith
- Blocking I/O systems
- Legacy systems with limited resources
- DB without indexing (full table scans)

**Why it happens:**
- Resource bottlenecks
- Bad design
- No scalability

---

### ⚡ 3. Low Latency + Low Throughput

**Meaning:**  
Fast but cannot scale

**Examples:**
- Single-threaded applications
- Desktop apps
- Local DB (SQLite)
- Small internal tools

**Why:**
- Optimized for single user
- No concurrency handling

👉 Good for small systems, bad for scale

---

### 🚚 4. High Latency + High Throughput

**Meaning:**  
Slow per request but handles large volume

**Examples:**
- Batch processing (Hadoop, Spark)
- Video rendering pipelines
- Kafka-based async systems
- Bulk email/SMS processing

**Why:**
- Work is queued
- Batch processing
- Throughput prioritized over response time

---

## ⚖️ Trade-offs

| Use Case            | Optimize For   |
|--------------------|---------------|
| User-facing APIs   | Low Latency   |
| Backend jobs       | High Throughput |
| Real-time systems  | Low Latency   |
| Batch systems      | High Throughput |

---

## 🧩 Real-World Insight

- **Low latency systems**
    - APIs, UI, trading systems
    - Require quick response

- **High throughput systems**
    - Data pipelines
    - Analytics
    - Background jobs

---

## 🧵 Java / Backend Perspective

- **Low Latency Techniques:**
    - Caching (Redis)
    - Efficient algorithms
    - Non-blocking I/O (WebFlux)

- **High Throughput Techniques:**
    - Thread pools
    - Async processing (@Async)
    - Message queues (Kafka, RabbitMQ)
    - Batch processing

---

## 🎯 Interview One-Liner

> Latency is the time taken to process a single request, while throughput is the number of requests processed per unit time. Systems balance them based on use case—real-time systems optimize latency, batch systems optimize throughput.
