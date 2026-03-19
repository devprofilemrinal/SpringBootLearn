# Java Interfaces – Complete Guide

## 1. What is an Interface?

An **interface in Java** is a reference type that defines a **contract**.
Classes implementing the interface must provide implementations for its methods.

Interfaces support **abstraction, polymorphism, and multiple inheritance**.

Example:

```java
interface Payment {
    void pay(double amount);
}

class CreditCardPayment implements Payment {

    public void pay(double amount) {
        System.out.println("Paid using credit card: " + amount);
    }
}
```

---

# 2. Why Interfaces Are Used

Interfaces are used for:

* Abstraction
* Multiple inheritance
* Loose coupling
* Contract-based design
* Polymorphism
* Testability

Example:

```java
Payment payment = new CreditCardPayment();
payment.pay(1000);
```

---

# 3. Basic Syntax

```java
interface InterfaceName {

    int CONSTANT = 10;

    void method1();

    void method2();
}
```

Implementation:

```java
class ClassName implements InterfaceName {

    public void method1() {
        // implementation
    }

    public void method2() {
        // implementation
    }
}
```

---

# 4. Rules of Interfaces

| Feature              | Rule                                      |
| -------------------- | ----------------------------------------- |
| Methods              | public abstract by default                |
| Variables            | public static final by default            |
| Constructor          | Not allowed                               |
| Object creation      | Cannot instantiate                        |
| Implementation       | Class must implement all abstract methods |
| Multiple inheritance | Supported                                 |

---

# 5. Interface Variables

All variables inside an interface are automatically:

```
public static final
```

Example:

```java
interface Config {

    int MAX_USERS = 100;
}
```

Usage:

```java
System.out.println(Config.MAX_USERS);
```

---

# 6. Interface Methods (Before Java 8)

Before Java 8, interfaces only contained **abstract methods**.

```java
interface Animal {

    void eat();
}
```

Implementation:

```java
class Dog implements Animal {

    public void eat() {
        System.out.println("Dog eating");
    }
}
```

---

# 7. Default Methods (Java 8)

Default methods allow implementation inside interfaces.

Purpose: **Backward compatibility**

```java
interface Vehicle {

    default void start() {
        System.out.println("Vehicle starting");
    }
}
```

Implementation:

```java
class Car implements Vehicle {
}
```

Usage:

```java
Car car = new Car();
car.start();
```

---

# 8. Static Methods (Java 8)

Static methods belong to the interface itself.

```java
interface MathUtil {

    static int square(int x) {
        return x * x;
    }
}
```

Usage:

```java
int result = MathUtil.square(5);
```

---

# 9. Private Methods (Java 9)

Private methods help reuse logic inside default methods.

```java
interface Logger {

    default void logInfo(String msg) {
        log(msg);
    }

    private void log(String msg) {
        System.out.println(msg);
    }
}
```

---

# 10. Functional Interfaces

A **functional interface** contains exactly **one abstract method**.

Used with **Lambda expressions**.

```java
@FunctionalInterface
interface Calculator {

    int calculate(int a, int b);
}
```

Lambda example:

```java
Calculator add = (a, b) -> a + b;

System.out.println(add.calculate(5, 3));
```

---

# 11. Common Functional Interfaces

| Interface | Purpose             |
| --------- | ------------------- |
| Predicate | returns boolean     |
| Function  | input → output      |
| Consumer  | consumes input      |
| Supplier  | provides output     |
| Runnable  | no input, no output |

Example:

```java
Predicate<Integer> isEven = n -> n % 2 == 0;
```

---

# 12. Marker Interfaces

Marker interfaces contain **no methods**.

They mark a class so JVM can enable behavior.

Examples:

* Serializable
* Cloneable
* RandomAccess

Example:

```java
class Student implements Serializable {
}
```

---

# 13. Multiple Inheritance with Interfaces

Java does not allow multiple inheritance with classes but allows it with interfaces.

```java
interface A {
    void methodA();
}

interface B {
    void methodB();
}

class C implements A, B {

    public void methodA() {
        System.out.println("A");
    }

    public void methodB() {
        System.out.println("B");
    }
}
```

---

# 14. Interface Inheritance

Interfaces can extend other interfaces.

```java
interface Animal {
    void eat();
}

interface Dog extends Animal {
    void bark();
}
```

Implementation:

```java
class Labrador implements Dog {

    public void eat() {
        System.out.println("Eating");
    }

    public void bark() {
        System.out.println("Barking");
    }
}
```

---

# 15. Default Method Conflict

When two interfaces define the same default method.

```java
interface A {

    default void show() {
        System.out.println("A");
    }
}

interface B {

    default void show() {
        System.out.println("B");
    }
}
```

Resolution:

```java
class C implements A, B {

    public void show() {
        A.super.show();
    }
}
```

---

# 16. Nested Interfaces

Interfaces can exist inside classes.

```java
class Outer {

    interface Inner {
        void display();
    }
}
```

Implementation:

```java
class Test implements Outer.Inner {

    public void display() {
        System.out.println("Hello");
    }
}
```

---

# 17. Anonymous Class with Interface

Interfaces can be implemented using anonymous classes.

```java
Runnable r = new Runnable() {

    public void run() {
        System.out.println("Running thread");
    }
};
```

---

# 18. Interface vs Abstract Class

| Feature              | Interface                  | Abstract Class      |
| -------------------- | -------------------------- | ------------------- |
| Multiple inheritance | Yes                        | No                  |
| Constructors         | No                         | Yes                 |
| Fields               | static final only          | Any                 |
| Methods              | abstract, default, static  | abstract + concrete |
| State                | Cannot hold instance state | Can hold state      |

---

# 19. Interfaces in Spring Boot

Spring heavily uses interfaces for **loose coupling and dependency injection**.

Example:

```java
public interface UserService {

    User getUserById(Long id);
}
```

Implementation:

```java
@Service
public class UserServiceImpl implements UserService {

    public User getUserById(Long id) {
        return repository.findById(id);
    }
}
```

---

# 20. Best Practices

* Keep interfaces small
* Follow **Interface Segregation Principle**
* Use functional interfaces for lambdas
* Prefer composition over inheritance
* Avoid too many default methods

---

# 21. Real World Example

Payment system example:

```java
interface PaymentService {

    void pay(double amount);
}

class UpiPayment implements PaymentService {

    public void pay(double amount) {
        System.out.println("Paid via UPI: " + amount);
    }
}

class CardPayment implements PaymentService {

    public void pay(double amount) {
        System.out.println("Paid via Card: " + amount);
    }
}
```

Usage:

```java
PaymentService payment = new UpiPayment();
payment.pay(500);
```

---

# 22. Interview Questions

### What is an interface?

An interface defines a contract containing abstract methods that implementing classes must provide.

### Can interfaces have constructors?

No.

### Can interfaces have variables?

Yes, but they are always **public static final**.

### Can interfaces contain implemented methods?

Yes using **default** and **static** methods.

### Can interfaces extend classes?

No. Interfaces can only extend **other interfaces**.

### Can we create objects of interfaces?

No, but we can create references.

```java
Payment p = new CreditCardPayment();
```

---

# 23. Key Takeaways

* Interfaces define **contracts**
* Support **multiple inheritance**
* Methods are **public abstract by default**
* Variables are **public static final**
* Java 8 introduced **default and static methods**
* Java 9 introduced **private methods**

---

# 24. Short Interview Summary (35 words)

An interface in Java defines a contract that implementing classes must follow. It supports abstraction and multiple inheritance. Methods are public abstract by default, variables are public static final, and Java 8 introduced default and static methods.
