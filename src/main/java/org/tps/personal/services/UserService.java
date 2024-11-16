package org.tps.personal.services;

import org.tps.personal.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

  private final List<User> users = new ArrayList<>();

  public UserService() {
    users.add(new User(1, "user1", "user1@example.com", new ArrayList<>()));
    users.add(new User(2, "user2", "user2@example.com", new ArrayList<>()));
  }

  public User getUserById(int id) {
    return users.stream()
        .filter(user -> user.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public List<User> getAllUsers() {
    return users;
  }

  public void addUser(User user) {
    users.add(user);
  }
}
