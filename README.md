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

Official definition

> Every module should have one single responsibility. 
> This means two separate concerns/responsibilities/tasks should always be implemented in separate modules. 
> Robert C. Martin defines a “responsibility” as a “reason to change”. If a module has several responsibilities, 
> there are several reasons to change this module, namely the requirements for each responsibility may change. 
> On the other hand a reason to change a module also means that it is the responsibility of the module to implement 
> the aspect that is changed.

In plain words

> Classes should have a single responsibility and thus only a single reason to change. 

Real-world example

> A postman is responsible for only one task, delivering mail to your house. 
> He is not in charge of and does not know how to do your taxes or repair your car. 
> Same as the lawyer or car technician do not know how to deliver mail, 
> so postman's responsibilities (`functions`) are limited (`encapsulated`) to him only

**Programmatic Example**

👎 BAD example:

```java
class UserController { 

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

Why is it bad?

`UserController.java` is responsible for writing to file **(!)**, fetching user data from a database **(!)** and serialization logic **(!!!)**. 

Possible solutions: 

- A
- B
- C