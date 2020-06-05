# SOLID programming principles 

## Definition

Wikipedia explains [SOLID](https://en.wikipedia.org/wiki/SOLID) in the following way:
> In object-oriented computer programming, 
> SOLID is a mnemonic acronym for five design principles intended to make software designs more
> understandable, flexible and maintainable. It is not related to the GRASP software design principles. 
> The principles are a subset of many principles promoted by American software engineer and instructor
> Robert C. Martin. Though they apply to any object-oriented design, the SOLID principles can also form a 
> core philosophy for methodologies such as agile development or adaptive software development.
> The theory of SOLID principles was introduced by Martin in his 2000 paper Design Principles
> and Design Patterns, although the SOLID acronym was introduced later by Michael Feathers.

and it stands for

* **S**ingle Responsibility Principle
* **O**pen-Closed Principle
* **L**iskov Substitution Principle
* **I**nterface Segregation Principle
* **D**ependency Inversion Principle


## Single Responsibility Principle (SRP)

**Official definition**

> Every module should have one single responsibility. 
> This means two separate concerns/responsibilities/tasks should always be implemented in separate modules. 
> Robert C. Martin defines a “responsibility” as a “reason to change”. If a module has several responsibilities, 
> there are several reasons to change this module, namely the requirements for each responsibility may change. 
> On the other hand a reason to change a module also means that it is the responsibility of the module to implement 
> the aspect that is changed.

**In plain words**

> Classes should have a single responsibility and thus only a single reason to change. 

**Real-world example**

> A postman is responsible for only one task, delivering mail to your house. 
> He is not in charge of and does not know how to do your taxes or repair your car. 
> Same for your lawyer and car technician, they do not know how to deliver mail, 
> so postman's responsibilities (`functions`) are limited (`encapsulated`) to him only

**Programmatic Example**

👎 BAD:

```java
class UserController { 
        
    public void updateUserStatus(User user, Status newStatus) {
        user.setStatus(newStatus);
    }

    public void writeUserToFile(User user, Path file) throws IOException {
        Files.write(file, serializeUser(user), StandardOpenOption.APPEND);
    }
    
    public byte[] serializeUser(User user) {
        return user.toString().getBytes();
    }
    
    public User readUserFromDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:8888:oracle");
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where rownum = 1");
    
        return new User(resultSet.getString(1), resultSet.getString(2));
    }
}
```

**Why is it bad?**

`UserController.java` is responsible for writing to file **(!)**, fetching user data from a database **(!)** and serialization logic **(!!!)**. 

**Possible solutions** 

- Move serialize method to a separate serialization class or use `ObjectMapper.java` 
- Any type of persistence (read/write from file, db, cloud) should be handled by a separate object 

👍 GOOD:

```java
class UserController { 
        
    public void updateUserStatus(User user, Status newStatus) {
        user.setStatus(newStatus);
    }
}

class UserPersistence {
    
    UserPersistence(ObjectSerializer<User> userSerializer) { this.userSerializer = userSerializer;  }
    
    public User readUser() throws ClassNotFoundException, SQLException {
        // code omitted 
    }
    
    public void writeUser(User user) {
        String s = userSerializer.serialize(user);
        // code omitted
    }
}

class ObjectSerializer<T> {
    
    String serialize(T object) {
        // code omitted
    }       
}

```
 
See also [BadJournal](src/main/java/com/github/eugenenosenko/solid/srp/bad/BadJournal.java) and [Journal](src/main/java/com/github/eugenenosenko/solid/srp/good/Journal.java),  [Persistence](src/main/java/com/github/eugenenosenko/solid/srp/good/Persistence.java)


## Open-Closed Principle (OCP)

**Official definition**
> The open/closed principle states "software entities (classes, modules, functions, etc.) 
> should be open for extension, but closed for modification"; that is, such an entity can allow its 
> behaviour to be extended without modifying its source code.


**In plain words**
> Existed and well-tested class should not be modified when a new feature needs to 
> be built. It may introduce a new bug when we modify an existing class to make a new feature.
> So rather than changing an existing class/Interface, we should extend that class/Interface 
> in a new class to add new features. 

So... 
> “Software entities … should be open for extension, but closed for modification.”


**Real-world example**

> Imagine you an inventor of a [screwdriver](https://en.wikipedia.org/wiki/Screwdriver), and you your first creation - a [flat-blade](https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/Screw_Driver_display.jpg/250px-Screw_Driver_display.jpg) screwdriver is 
> a great success but people contact you and say that they want to have a [Phillips or Frearson](https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Frearson_vs_Phillips.svg/800px-Frearson_vs_Phillips.svg.png) screw heads and not only flat-blade ones. So what do you do? You need to go back to the drawing board, 
> create a new iron cast, test the screwdriver with a new tip. It works and everyone is happy, until people come back to you and say they want 3 more types, then 5 more types, then 10 more types, you end up with [20+ forms](https://www.mechanicalbooster.com/wp-content/uploads/2018/05/Different-Types-of-Scredrivers.jpg).
> Maybe it would've been better to create a single handle but extend it with [multiple tips](https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Set_of_security_screw_driver_bits.jpg/1280px-Set_of_security_screw_driver_bits.jpg)? 

**Programmatic Example**

👎 BAD:


```java

enum Color { RED, GREEN, BLUE }
enum Size { SMALL, MEDIUM, LARGE, HUGE }
enum Category { HOME, OFFICE, SPORT }

class ProductFilter {
  // initial implementation
  public Stream<Product> filterByColor(List<Product> products, Color color) {
    return products.stream().filter(p -> p.color == color);
  }
  
  
  public Stream<Product> filterBySize(List<Product> products, Size size) {
    return products.stream().filter(p -> p.size == size);
  }
  
  // added filter after requirements change
  public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color) {
    return products.stream().filter(p -> p.size == size && p.color == color);
  }
  //  
  // additional filters to filter category, size + category, category + color, category + size + color;
  // which leads to state space explosion (combinatorial explosion)  
  // See https://en.wikipedia.org/wiki/Model_checking & https://en.wikipedia.org/wiki/Combinatorial_explosion
}

```

**Why is it bad?**

**Possible solutions** 

- 
- 
👍 GOOD:
```java


```

## Liskov Substitution Principle (LSP)

**Official definition**

**In plain words**

**Real-world example**

**Programmatic Example**

👎 BAD:

```java


```
**Why is it bad?**

**Possible solutions** 

- 
- 
👍 GOOD:
```java


```

## Interface Segregation Principle (ISP)

**Official definition**

**In plain words**

**Real-world example**

**Programmatic Example**

👎 BAD:

```java


```
**Why is it bad?**

**Possible solutions** 

- 
- 
👍 GOOD:
```java


```

## Dependency Inversion Principle (DIP)

**Official definition**

**In plain words**

**Real-world example**

**Programmatic Example**

👎 BAD:

```java


```
**Why is it bad?**

**Possible solutions** 
- 
- 
👍 GOOD:

```java


```