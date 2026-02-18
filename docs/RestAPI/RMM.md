# Richardson Maturity Model (RMM): The Path to REST

The **Richardson Maturity Model** is a way to grade your API's "RESTfulness." Developed by Leonard Richardson, it breaks down the core concepts of REST into four distinct levels (0 to 3).

---

## Level 0: The Swamp of POX (Plain Old XML)
At this level, HTTP is used merely as a transport protocol for Remote Procedure Calls (RPC).

* **Characteristics:**
    * Single URI (Endpoint).
    * Single HTTP Method (usually `POST`).
    * The message body contains the operation name and parameters.
* **The Problem:** It doesn't leverage any of the web’s native capabilities. It’s basically just tunneling data through HTTP.

## Level 1: Resources
This level introduces the concept of **Resources**. Instead of talking to a single "service" endpoint, you talk to individual entities.

* **Characteristics:**
    * Multiple URIs (e.g., `/users`, `/orders/123`).
    * Each resource has its own unique identity.
* **The Limitation:** It still typically relies on one HTTP method (often `POST`) for all actions (create, update, delete).

## Level 2: HTTP Verbs
This is the "sweet spot" for most professional APIs. It introduces standard **HTTP Methods** and **Status Codes**.

* **Characteristics:**
    * **GET:** To fetch data (Safe/Idempotent).
    * **POST:** To create new data.
    * **PUT/PATCH:** To update data.
    * **DELETE:** To remove data.
    * **Standard Codes:** Using `201 Created` for success, `404 Not Found` for missing resources, and `400 Bad Request` for validation errors.
* **Benefit:** Provides a predictable interface that follows web standards.

## Level 3: Hypermedia Controls (HATEOAS)
The final level, often called **Hypermedia as the Engine of Application State**.

* **Characteristics:**
    * The API response includes **links** to related actions.
    * The client doesn't need to hardcode URIs; it "discovers" what it can do next by reading the links in the response.
* **Example Response Snippet:**
    ```json
    {
      "orderId": 55,
      "status": "shipped",
      "_links": {
        "self": { "href": "/orders/55" },
        "track": { "href": "/orders/55/tracking" }
      }
    }
    ```

---

## Summary Table

| Level | Focus | Summary |
| :--- | :--- | :--- |
| **0** | Transport | One URI, One Method (The "Swamp"). |
| **1** | Resources | Multiple URIs, One Method. |
| **2** | Verbs | Multiple URIs, Multiple Standard Methods. |
| **3** | Hypermedia | Discoverability via Links (HATEOAS). |

---

> **Note for Juniors:** While Level 3 is the "purest" form of REST, **Level 2** is the standard for 90% of commercial applications. Don't over-engineer for Level 3 unless the project specifically requires high discoverability.