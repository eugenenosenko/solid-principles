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


##Single Responsibility Principle (SRP)

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

See also [examples] (src\main\java\com\github\eugenenosenko\solid\ocp\good)

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