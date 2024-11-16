package org.tps.personal.models;

import java.util.List;

public class User {

  private int id;
  private String username;
  private String email;
  private List<Friend> friends;

  public User(int id, String username, String email, List<Friend> friends) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.friends = friends;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Friend> getFriends() {
    return friends;
  }

  public void setFriends(List<Friend> friends) {
    this.friends = friends;
  }
}
