package org.tps.personal.services;

import org.tps.personal.models.Friend;
import org.tps.personal.models.User;

import java.util.List;

public class FriendService {

  private final UserService userService;

  public FriendService(UserService userService) {
    this.userService = userService;
  }

  public void addFriend(int userId, Friend friend) {
    User user = userService.getUserById(userId);
    if (user != null) {
      user.getFriends().add(friend);
    }
  }

  public List<Friend> getFriends(int userId) {
    User user = userService.getUserById(userId);
    return user != null ? user.getFriends() : null;
  }
}
