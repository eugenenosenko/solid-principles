package com.github.eugenenosenko.solid.dip.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
  // refactor code below so that it doesn't violate DIP
}

class CachedSQLDatabaseConnection {
  private Map<String, String> cachedQueries = new HashMap<>();

  String doSelect(String sqlString) {
    System.out.println("Selecting from DB. SQL String: " + sqlString);
    return cachedQueries.putIfAbsent(sqlString, "dummy select result");
  }

  String doDelete(String sqlString) {
    System.out.println("Deleting from DB. SQL String: " + sqlString);
    cachedQueries.clear();
    return "dummy result";
  }

  String doInsert(String sqlString) {
    System.out.println("Inserting into DB. SQL String: " + sqlString);
    cachedQueries.clear();
    return "dummy result";
  }

  String doUpdate(String sqlString) {
    System.out.println("Updating DB. SQL String: " + sqlString);
    cachedQueries.clear();
    return "dummy result";
  }

  public Map<String, String> getCachedQueries() {
    return cachedQueries;
  }
}

class User {
  String name;
  String lastName;

  public User(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }
}

class UserPersistenceService {
  private final CachedSQLDatabaseConnection databaseConnection;
  private final List<User> savedUsers = new ArrayList<>();

  UserPersistenceService(CachedSQLDatabaseConnection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  void saveUser(User user) {
    databaseConnection.doInsert(user.toString());
    savedUsers.add(user);
  }

  void getUser(User user) {
    databaseConnection.doSelect(user.toString());
  }

  List<User> getSavedUsers() {
    return savedUsers;
  }
}
