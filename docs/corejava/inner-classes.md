# Java Inner Classes – Complete Guide

---

# 1️⃣ What Are Inner Classes?

An **Inner Class** is a class defined **inside another class**.

It helps logically group classes that are **closely related** and can access the **members (including private members)** of the outer class.

Example:

```java
class Outer {

    class Inner {
        void show() {
            System.out.println("Inner class method");
        }
    }
}
```

---

# 2️⃣ Why Inner Classes Exist

Inner classes help:

- Improve **encapsulation**
- Group **related classes together**
- Reduce namespace pollution
- Provide access to outer class members

Common uses:

- Event handling
- Callbacks
- Helper classes
- Builder patterns

---

# 3️⃣ Types of Inner Classes

Java provides **4 types of inner classes**:

1. Member Inner Class
2. Static Nested Class
3. Local Inner Class
4. Anonymous Inner Class

---

# 4️⃣ Member Inner Class (Non-static)

A **member inner class** is declared inside a class but **outside methods**.

It behaves like a **member of the outer class**.

Example:

```java
class Outer {

    private int data = 10;

    class Inner {
        void display() {
            System.out.println("Data: " + data);
        }
    }
}
```

### Creating Object

```java
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
inner.display();
```

### Key Points

- Cannot have static members
- Has access to **all outer class members**
- Requires outer class object to instantiate

---

# 5️⃣ Static Nested Class

A **static nested class** is declared with the `static` keyword.

It behaves like a **static member of outer class**.

Example:

```java
class Outer {

    static class Nested {
        void show() {
            System.out.println("Static nested class");
        }
    }
}
```

### Creating Object

```java
Outer.Nested obj = new Outer.Nested();
obj.show();
```

### Key Points

- Does NOT require outer class instance
- Can access only **static members of outer class**

---

# 6️⃣ Local Inner Class

A **local inner class** is declared **inside a method**.

Example:

```java
class Outer {

    void display() {

        class LocalInner {
            void show() {
                System.out.println("Local inner class");
            }
        }

        LocalInner obj = new LocalInner();
        obj.show();
    }
}
```

### Key Points

- Scope limited to the method
- Cannot access non-final local variables (before Java 8)
- Useful for **method-specific logic**

---

# 7️⃣ Anonymous Inner Class

An **anonymous inner class** is a class **without a name** used for one-time implementation.

Example:

```java
interface Greeting {
    void sayHello();
}

public class Test {
    public static void main(String[] args) {

        Greeting g = new Greeting() {
            public void sayHello() {
                System.out.println("Hello");
            }
        };

        g.sayHello();
    }
}
```

### Key Points

- No class name
- Used for **one-time implementation**
- Often used in **event listeners**

---

# 8️⃣ Inner Class Access to Outer Members

Inner classes can access:

- private members
- protected members
- default members
- public members

Example:

```java
class Outer {

    private int x = 10;

    class Inner {
        void print() {
            System.out.println(x);
        }
    }
}
```

---

# 9️⃣ Real-World Usage

Inner classes are used in:

- Event handling (Swing / Android)
- Callback implementations
- Builder design pattern
- Comparator implementations
- Runnable implementations

Example:

```java
Thread t = new Thread(new Runnable() {
    public void run() {
        System.out.println("Thread running");
    }
});
```

---

# 🔟 Anonymous Class vs Lambda

Before Java 8:

```java
Runnable r = new Runnable() {
    public void run() {
        System.out.println("Run");
    }
};
```

After Java 8:

```java
Runnable r = () -> System.out.println("Run");
```

Lambda replaced many anonymous classes.

---

# 1️⃣1️⃣ Comparison of Inner Class Types

| Type | Location | Requires Outer Object |
|-----|----------|----------------------|
| Member Inner | Inside class | Yes |
| Static Nested | Inside class (static) | No |
| Local Inner | Inside method | Yes |
| Anonymous | Inline | Yes |

---

# 1️⃣2️⃣ Advantages of Inner Classes

- Better encapsulation
- Cleaner code structure
- Logical grouping
- Access to outer class members
- Reduces code complexity

---

# 1️⃣3️⃣ Interview Questions

- What are inner classes in Java?
- Types of inner classes?
- Difference between **inner class and static nested class**?
- Can inner class have static methods?
- Why anonymous classes used?
- Anonymous class vs lambda?

---

# 1️⃣4️⃣ Interview Summary (≈30 Words)

Inner classes are classes defined inside another class to logically group related functionality. Java supports member, static nested, local, and anonymous inner classes for better encapsulation and structured code organization.

---