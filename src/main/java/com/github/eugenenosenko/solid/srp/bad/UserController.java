package com.github.eugenenosenko.solid.srp.bad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// representative definition only
class User {
  User(String string, String string1) {}
}

public class UserController {

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
