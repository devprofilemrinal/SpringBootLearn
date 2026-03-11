# Java Serialization – Complete Guide

---

# 1️⃣ What is Serialization?

**Serialization** is the process of converting a Java object into a **byte stream** so that it can be:

- Stored in a file
- Sent over a network
- Saved in a database
- Transferred between systems

Later, the byte stream can be converted back into an object using **Deserialization**.

---

# 2️⃣ Why Serialization Is Used

Serialization is commonly used for:

- Saving object state
- Network communication (RMI, sockets)
- Distributed systems
- Caching
- Messaging systems (Kafka, RabbitMQ)

Example scenario:

Saving an object to disk so it can be restored later.

---

# 3️⃣ Serialization Process

Steps involved:

1. Class must implement `Serializable`
2. Create object
3. Use `ObjectOutputStream`
4. Write object to stream

Flow:

```
Java Object
     ↓
Serialization
     ↓
Byte Stream
     ↓
File / Network / Storage
```

---

# 4️⃣ Example – Serialization

```java
import java.io.*;

class User implements Serializable {

    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class SerializeExample {

    public static void main(String[] args) throws Exception {

        User user = new User(1, "Rahul");

        FileOutputStream file = new FileOutputStream("user.ser");
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(user);

        out.close();
        file.close();

        System.out.println("Object serialized");
    }
}
```

---

# 5️⃣ Deserialization

Deserialization converts a **byte stream back into a Java object**.

Flow:

```
Byte Stream
     ↓
Deserialization
     ↓
Java Object
```

---

# 6️⃣ Example – Deserialization

```java
import java.io.*;

public class DeserializeExample {

    public static void main(String[] args) throws Exception {

        FileInputStream file = new FileInputStream("user.ser");
        ObjectInputStream in = new ObjectInputStream(file);

        User user = (User) in.readObject();

        in.close();
        file.close();

        System.out.println("User restored");
    }
}
```

---

# 7️⃣ Serializable Interface

To enable serialization:

```java
class User implements Serializable {
}
```

Important:

`Serializable` is a **marker interface**.

It has:

- No methods
- Only signals JVM that object can be serialized.

---

# 8️⃣ serialVersionUID (Very Important)

`serialVersionUID` is a **unique identifier for a serialized class**.

Example:

```java
private static final long serialVersionUID = 1L;
```

Why needed?

When deserializing:

- JVM compares UID in class
- If mismatch → `InvalidClassException`

Best practice:

Always define `serialVersionUID`.

---

# 9️⃣ transient Keyword

`transient` prevents a field from being serialized.

Example:

```java
class User implements Serializable {

    int id;
    String name;

    transient String password;
}
```

Here:

- `password` will NOT be serialized.

Use cases:

- Sensitive data
- Temporary fields
- Derived values

---

# 🔟 static Fields and Serialization

Static variables belong to **class**, not object.

Therefore:

Static fields are **not serialized**.

Example:

```java
static int counter;
```

When deserialized:
- Value will not be restored.

---

# 1️⃣1️⃣ Custom Serialization

You can control serialization using:

```java
private void writeObject(ObjectOutputStream out)
private void readObject(ObjectInputStream in)
```

Example:

```java
private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
}

private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
}
```

Used for:

- Encryption
- Validation
- Custom logic

---

# 1️⃣2️⃣ Externalizable Interface

Alternative to Serializable.

Example:

```java
class User implements Externalizable {

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
    }

    public void readExternal(ObjectInput in) throws IOException {
        id = in.readInt();
    }
}
```

Difference:

| Serializable | Externalizable |
|--------------|---------------|
| Automatic | Manual |
| Less control | Full control |

---

# 1️⃣3️⃣ Advantages of Serialization

- Easy object persistence
- Simple network transfer
- Built-in Java support
- Supports distributed systems

---

# 1️⃣4️⃣ Disadvantages

- Performance overhead
- Security risks
- Tight class coupling
- Version compatibility issues

---

# 1️⃣5️⃣ Real-World Uses

Serialization is used in:

- RMI (Remote Method Invocation)
- HTTP session storage
- Distributed caching
- Message queues
- Microservices communication

---

# 1️⃣6️⃣ Interview Questions

- What is serialization?
- Difference between serialization and deserialization?
- What is serialVersionUID?
- Why Serializable is marker interface?
- What does transient do?
- Are static fields serialized?
- Serializable vs Externalizable?

---

# 1️⃣7️⃣ Interview Summary (≈30 words)

Serialization converts a Java object into a byte stream for storage or transmission, while deserialization restores it back into an object. It requires implementing Serializable and supports version control using serialVersionUID.

---