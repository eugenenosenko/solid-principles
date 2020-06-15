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

- `UserController.java` is responsible for writing to file **(!)**, fetching user data from a database **(!!)** and serialization logic **(!!!)**. 

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
    
    public User readUser()  throws SQLException {
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

class ProductFilter {
  // initial implementation
  public Stream<Product> filterByColor(List<Product> products, Color color) {
    return products.stream().filter(p -> p.color == color);
  }
  
  
  public Stream<Product> filterBySize(List<Product> products, Size size) {
    return products.stream().filter(p -> p.size == size);
  }
  
  // new feature to sort by size and color requires a change in code
  public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color) {
    return products.stream().filter(p -> p.size == size && p.color == color);
  }
}
```

**Why is it bad?**
- Chance of breaking existing functionality
- Each subsequent change to the original code requires rigorous testing and verifying of original functionality.

**Possible solutions** 
- Extend / implement `ProductFilter.java` with custom filtering method
- If `ProductFilter.java` is final, then use delegation instead
- When designing your own library make sure that the code that will be used by other developers is generic and open for extension

👍 GOOD:
```java
enum Category { HOME, OFFICE, SPORT }

class CategoryProductFilter extends ProductFilter {
    
    public Stream<Product> filterBySizeAndCategory(List<Product> products, Size size, Category category) {
        return super.filterBySize(products, size).filter(product -> product.getCategory() == category);
    }
}

```
👍 BEST:

Design patterns i.e. `Specification` design patterns make it easier to
create code that is open for extension and closed for modification

```java
interface Specification<T> {
  boolean isSatisfied(T item);
}

interface Filter<T> {
  Stream<T> filter(List<T> items, Predicate<T> spec);
}

class BetterFilter {

      @Override
      public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(spec::test);
      }
}

public class SizeSpecification implements Specification<Product> {
  private final Size size;

  public SizeSpecification(Size size) { this.size = size; }

  @Override
  public boolean test(Product p) { return p.size == size; }
}
```

See also [ocp folder](src/main/java/com/github/eugenenosenko/solid/ocp) for examples

## Liskov Substitution Principle (LSP)

**Official definition**
> "Objects in a program should be replaceable with instances of their subtypes without altering the correctness of that program."

> Liskov's notion of a behavioural subtype defines a notion of substitutability for objects; that is, if S is a subtype of T, 
> then objects of type T in a program may be replaced with objects of type S without altering any of the desirable properties of that program

**In plain words**
> Derived classes (sub-classes) should extend the base classes without changing their behavior.

**Real-world example**
> You have a **calculator** in your smartphone, it has some basic functions like add, subtract, multiply and divide and other functions. 
> But if you rotate your phone horizontally your basic calculator will change to a **scientific calculator** with additional functions like calculating 
> sin / cos / log / tan etc. In this case  **scientific calculator** is a subtype of **calculator** and what `LSP` says is that scientific calculator's functions
> like add / subtract / multiply / divide should behave **exactly** the same of those of basic calculator, i.e. `2 * 2` should return `4` whether you are using scientific 
> calculator or basic calculator.


**Programmatic Example**
👎 BAD:
```java

class Rectangle {
    int width, height;

    void setWidth(int newWidth) { this.width = newWidth; }
    void setHeight(int newHeight) { this.height = newHeight; }
    int getArea () { return width * height; }
}

class Square extends Rectangle {
    Square(int side) { this.height = this.width = side; }  
  
    @Override
    void setWidth(int newWidth) { 
        super.setWidth(newWidth); 
        super.setHeight(newWidth); 
    }

    @Override
    void setHeight(int newHeight) { 
        super.setWidth(newWidth); 
        super.setHeight(newWidth); 
    }
}
```
**Why is it bad?**
- In `Square.java` in the methods `setWidth` and `setHeight` we are trying to enforce the square property of the 
  rectangle by making sure height and width are the same but by now `Square` can't substitute `Rectangle`. See example : 
- This way, Rectangle can no longer be substituted for a Square because they behave differently

```java
class Demo {
    public static void main(String... args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(5);
        rectangle.setWidth(6);
        
        // returns 30!
        System.out.println(rectangle.getArea());
        
        Rectangle square = new Square();
        square.setHeight(5);
        square.setWidth(6);
        
        // returns 36!
        System.out.println(square.getArea());

        // false
        assert square.getArea() == rectangle.getArea();
    }
}

```  
**Possible solutions** 
- Avoid extending a base class if you know that the basic behaviour needs to be overridden. 
- If you are creating a base class you might want to consider to make methods responsible for its behaviour as `final` to make sure LSP is not violated
- Consider `Factory` design pattern if you want to create an object with a specific set of attributes 

👍 GOOD:
```java
interface ShapeSpecification<T extends Shape> {
   boolean isSatisfied(T item);
}

class SquareSpecification implements ShapeSpecification<Rectangle> {
    boolean isSatisfied(Rectangle r) { r.getHeight() == r.getWidth(); }
}

class Rectangle extends Shape {
    int width, height;

    void setWidth(int newWidth) { this.width = newWidth; }
    void setHeight(int newHeight) { this.height = newHeight; }
    int getArea () { return width * height; }
}    

class ShapeFactory {
    public static Rectangle createSquare(int side) { 
        Rectangle rect = new Rectangle();
        rect.setWidth(side);
        rect.setHeight(side);
        return rect;
    }   
}
```
See [lsp folder](src/main/java/com/github/eugenenosenko/solid/lsp) for more examples 


## Interface Segregation Principle (ISP)

**Official definition**
> In the field of software engineering, the interface-segregation principle (ISP) states that no client should be forced 
> to depend on methods it does not use. ISP splits interfaces that are very large into smaller and more specific ones so that 
> clients will only have to know about the methods that are of interest to them. Such shrunken interfaces are also called role interfaces.
> ISP is intended to keep a system decoupled and thus easier to refactor, change, and redeploy

**In plain words**
> Several, specific, interfaces are better than one general-purpose interface (fat interface)

**Programmatic Example**

👎 BAD:

```java

public interface Printer {
  void print(String text);
  void scan();
  void fax();
}

public class MultiFunctionalPrinter implements Printer {
  @Override
  public void print(String text) { System.out.println("Printing" + " " + text); }

  @Override
  public void scan() { System.out.println("Scanning..."); }

  @Override
  public void fax() { System.out.println("Faxing..."); }
}


public class SimplePrinter implements Printer {
  @Override
  public void print(String text) { System.out.println("Printing: " + text); }

  @Override
  public void scan() { /* no implementation */ }

  @Override
  public void fax() { /* no implementation */ }
}
```

**Why is it bad?**
- By using this complex `Printer` interface you're giving the user of the `SimplePrinter` an indication 
  that you are supporting `scan` and `fax` functions, which might lead to surprises when they realise nothing is happening


**Possible solutions** 
- Breaking the complex, general-purpose interface into a several smaller ones that are more specific 
- Avoid creating interfaces if you know that classes that will be implementing them, might not be able to implement all the methods

👍 GOOD:
```java

public interface Fax { 
    void fax();
}

public interface Printer {
  void print(String text);
}

public interface Scanner {
  void scan();
}

public class ScanningPrinter implements Printer, Scanner {
  @Override
  public void print(String text) {
    System.out.println("Printing " + text);
  }

  @Override
  public void scan() {
    System.out.println("Scanning");
  }
}
```

See [isp folder](src/main/java/com/github/eugenenosenko/solid/isp) for examples 

## Dependency Inversion Principle (DIP)

**Official definition**
> Dependency inversion principle is a specific form of decoupling software modules. When following this principle, 
> the conventional dependency relationships established from high-level, policy-setting modules to low-level, 
> dependency modules are reversed, thus rendering high-level modules independent of the low-level module implementation details. 
> The principle states: 
>
> 1) High-level modules should not depend on low-level modules. Both should depend on abstractions (e.g. interfaces).
> 2) Abstractions should not depend on details. Details (concrete implementations) should depend on abstractions.


**In plain words**
> High-level modules ( classes / modules that deal with business logic ) should not directly use classes / modules that
> operate on data but instead talk to those classes via a layer of abstraction, i.e. an interface

**Programmatic Example**

👎 BAD:

```java
class User {
    public final String name,lastName;
    private String id; 

    User(String name, String lastName) { 
        this.name = name; 
        this.lastaName= lastName; 
    }   
}


class UserRetriever { // low-level module
    private final List<User> users = new ArrayList<>();    
    
    UserRetriever() {
        this.users = fetchUserFromWeb();    
    }
    
    // (!) exposing storage implementation as a public getter
    List<User> getUsers() { return users; }
}


class UserSearchService { // high-level module

    private final UserRetriever userRetriever;

    UserSearcherService(UserRetriver userRetriever) {
        this.userRetriever = userRetriever;
    }   

    public User getUserWithName(String name) {
        return userRetriever.getUsers().stream()
            .filter ( user -> user.name.equals(name))
            .findFirst()
            .orElseThrow();
    }
}
```

**Why is it bad?**
- `UserRetriever` is a low-level module because it's responsibility is user list manipulation. That is its only responsibility (see Single Responsibility Principle), but it exposes its internal data storage via a getter, so it's functionality is not encapsulated **(!)**
- `UserSearchService` on the other hand is a high-level module because it's closer to the business logic, and it depends on `UserRetriever`, 
a lower-level module and if for example we at some point will not want to use `List<User>` but `Map` or `Set` we can't easily do that because it's hardwired via `List<User> getUsers()`
- 

**Possible solutions** 
- Introduce an interface layer and make UserRetriever implement it. Interface should expose `high-level` API that is related to data manipulation.
- `UserSearchService` should depend on that interface instead of `UserRetriever`
- Remove `getUsers()` method from `UserRetriever` and replace it with high level API 


👍 GOOD:
```java

class User {
   // same as before
}
interface UserRetriever  {
  Optional<User> getUserWithId(int id);
  Optional<List<User>> getUsersWithName(String name) ;
}


class WebUserRetriever implements UserRetriever  { // low-level module
    // internal storage is no longer exposed
    private final List<User> users = new ArrayList<>();    
    
    WebUserRetriever() {
        this.users = fetchUserFromWeb();    
    }
    
    @Override
    Optional<User> getUserWithId(int id) {
    return userRetriever.getUsers().stream()
                .filter ( user -> user.id == id)
                .findFirst();
    }
    
    @Override
    Optional<List<User>> getUsersWithName(String name) {
       // implementation
    }
}


class UserController { // high-level module
    private final UserRetriever userRetriever;

    UserController(UserRetriver userRetriever) {
        this.userRetriever = userRetriever;
    }   

    public boolean isUserAllowedToAccessResource(String username) {
        Optional<List<User>> usersWithName = userRetriever.getUsersWithName(name);
        if (usersWithName.isPresent()) {
            return usersWithName.get().equals("admin");            
        }
        return false;
    }
}
```

In the above example the responsibility of manipulating low-level data is encapsulated within `WebUserRetriever.java` and is not exposed outside of it. 
A high-level module like `UserController` is dependant of an abstraction: `UserRetriever` interface and if it will want to use some other retriever, DatabaseUserRetriever it can do so without changes to source code.

-----------------------------------------------------------------------

### Summary 

Remember that SOLID is a tool and not necessarily the goal itself. Sometimes when starting to work on complex designs 
it is hard to know right from the start whether you are using the correct abstractions. 

All you have is your best guess of what the application should look like, so first and foremost try to make sure that you design your 
application to be as simple as possible and when you need to reduce complexity / refactor your application make sure to apply SOLID where possible

It's important to have SOLID principles at the back of your head but if you're not sure about the design make sure that 
you follow below rules:

- Be explicit, your classes & routines. They have to express clear intent.
- **Never** expose internal storage implementation.
- Remove duplications.
- Your code needs to pass all tests.
- Don't introduce functionality that you don't need right now (YAGNI).

But, remember that when you refactor your code, you should **definetly** apply SOLID principles.  
