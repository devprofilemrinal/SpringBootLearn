# Java Comparable vs Comparator – Complete Guide

---

# 1️⃣ Why Sorting is Important

In real applications, we often need to sort data:

- Students by marks
- Employees by salary
- Orders by date

Java provides two ways to sort objects:

```
Comparable → natural sorting
Comparator → custom sorting
```

---

# 2️⃣ What is Comparable?

`Comparable` is an interface used to define the **natural ordering** of objects.

Package:

```
java.lang
```

---

## Method

```java
int compareTo(T o);
```

---

## Example – Comparable

```java
class Student implements Comparable<Student> {

    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Student s) {
        return this.id - s.id;
    }

}
```

---

## Usage

```java
List<Student> list = new ArrayList<>();

list.add(new Student(3, "A"));
list.add(new Student(1, "B"));
list.add(new Student(2, "C"));

Collections.sort(list);
```

Sorting happens based on `id`.

---

# 3️⃣ Rules of compareTo()

Return value meaning:

| Return | Meaning |
|------|--------|
| Negative | Current object < other |
| Zero | Equal |
| Positive | Current object > other |

Example:

```java
return this.id - s.id;
```

---

# 4️⃣ What is Comparator?

`Comparator` is used for **custom sorting logic**.

Package:

```
java.util
```

---

## Method

```java
int compare(T o1, T o2);
```

---

## Example – Comparator

```java
class NameComparator implements Comparator<Student> {

    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);
    }

}
```

---

## Usage

```java
Collections.sort(list, new NameComparator());
```

Sorting happens based on `name`.

---

# 5️⃣ Comparable vs Comparator

| Feature | Comparable | Comparator |
|--------|-----------|-----------|
| Package | java.lang | java.util |
| Method | compareTo() | compare() |
| Sorting type | Natural | Custom |
| Modifies class | Yes | No |
| Multiple sorting | No | Yes |

---

# 6️⃣ Multiple Sorting Using Comparator

Example:

Sort by:

1. Name
2. If equal → ID

---

### Example

```java
class StudentComparator implements Comparator<Student> {

    public int compare(Student s1, Student s2) {

        int nameCompare = s1.name.compareTo(s2.name);

        if(nameCompare != 0) {
            return nameCompare;
        }

        return s1.id - s2.id;
    }

}
```

---

# 7️⃣ Comparator Using Lambda (Java 8+)

Modern approach.

Example:

```java
Collections.sort(list, (s1, s2) -> s1.id - s2.id);
```

---

### Using Comparator Utility Methods

```java
list.sort(Comparator.comparing(Student::getName));
```

---

### Multiple Fields

```java
list.sort(
    Comparator.comparing(Student::getName)
              .thenComparing(Student::getId)
);
```

---

# 8️⃣ Reverse Sorting

Example:

```java
Collections.sort(list, Comparator.reverseOrder());
```

Custom reverse:

```java
list.sort((s1, s2) -> s2.id - s1.id);
```

---

# 9️⃣ Real World Example

Example: Employee sorting.

```
Sort by salary
Then by name
```

Using Comparator:

```java
list.sort(
    Comparator.comparing(Employee::getSalary)
              .thenComparing(Employee::getName)
);
```

---

# 🔟 Common Mistakes

---

### ❌ Using subtraction (overflow risk)

```java
return a - b;
```

Better:

```java
return Integer.compare(a, b);
```

---

### ❌ Not handling null values

Use:

```java
Comparator.nullsFirst(...)
Comparator.nullsLast(...)
```

---

# 1️⃣1️⃣ When to Use What?

Use Comparable when:

- Single natural ordering exists

Use Comparator when:

- Multiple sorting logics needed
- Sorting logic should not modify class

---

# 1️⃣2️⃣ Interview Questions

- Difference between Comparable and Comparator?
- What is compareTo()?
- What is compare()?
- Can we use both together?
- How to sort by multiple fields?
- Why Comparator preferred in real-world?

---

# 1️⃣3️⃣ Key Concepts to Remember

```
Comparable → default sorting
Comparator → flexible sorting
```

Comparator is more powerful and widely used.

---

# 1️⃣4️⃣ Interview Summary (≈30 words)

Comparable defines natural ordering within a class using compareTo, while Comparator provides external custom sorting logic using compare, enabling flexible and multiple sorting strategies without modifying the original class.

---


# Production Ready Example – Comparable + Comparator

---

# 1️⃣ Student Class (Production Ready)

```java
import java.util.Objects;

public class Student implements Comparable<Student> {

    private final int id;
    private final String name;
    private final double marks;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    // Natural ordering → by id
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);
    }

    // equals based on id (business key can vary)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", marks=" + marks +
                '}';
    }
}
```

---

# 2️⃣ Custom Comparator (Multiple Conditions)

Sort logic:

1. First by **name**
2. If name same → sort by **id**

```java
import java.util.Comparator;

public class StudentNameIdComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {

        // Handle nulls safely
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return -1;
        if (s2 == null) return 1;

        // First condition → name
        int nameCompare = s1.getName().compareTo(s2.getName());

        if (nameCompare != 0) {
            return nameCompare;
        }

        // Second condition → id
        return Integer.compare(s1.getId(), s2.getId());
    }
}
```

---

# 3️⃣ Usage Example

```java
import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Student> list = new ArrayList<>();

        list.add(new Student(3, "Rahul", 85));
        list.add(new Student(1, "Amit", 90));
        list.add(new Student(2, "Rahul", 75));

        // Natural sorting (by id)
        Collections.sort(list);
        System.out.println("Sorted by ID:");
        list.forEach(System.out::println);

        // Custom sorting (name → id)
        list.sort(new StudentNameIdComparator());
        System.out.println("\nSorted by Name, then ID:");
        list.forEach(System.out::println);
    }
}
```

---

# 4️⃣ Java 8+ Cleaner Comparator (Recommended)

Same logic using lambda:

```java
list.sort(
    Comparator.comparing(Student::getName)
              .thenComparing(Student::getId)
);
```

---

# 5️⃣ Why This Is Production Ready

- Uses `Integer.compare()` → avoids overflow
- Proper `equals()` and `hashCode()`
- Immutable fields (`final`)
- Null-safe comparator
- Clear separation of concerns

---

# 6️⃣ Interview Tips

- Always use `compare()` methods instead of subtraction
- Keep natural ordering simple (usually ID)
- Use Comparator for business sorting
- Ensure consistency with `equals()` when needed

---

# 7️⃣ Interview Summary (≈30 words)

A production-ready Comparable defines natural ordering, while Comparator enables flexible multi-condition sorting with safe comparisons, null handling, and clean separation of concerns for maintainable and scalable Java applications.

---