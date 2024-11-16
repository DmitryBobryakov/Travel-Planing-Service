package org.tps.personal.controllers;

import org.tps.personal.models.Friend;
import org.tps.personal.services.FriendService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class FriendController {

  private FriendService friendService;

  public FriendController(FriendService friendService) {
    this.friendService = friendService;
  }

  public Route getFriends = (Request request, Response response) -> {
    int userId = Integer.parseInt(request.params(":id"));
    List<Friend> friends = friendService.getFriends(userId);
    if (friends != null) {
      return friends.toString();
    } else {
      response.status(404);
      return "Friends not found";
    }
  };

  public Route addFriend = (Request request, Response response) -> {
    int userId = Integer.parseInt(request.params(":id"));
    int friendId = Integer.parseInt(request.queryParams("friendId"));
    String friendName = request.queryParams("friendName");
    friendService.addFriend(userId, new Friend(friendId, friendName));
    return "Friend added";
  };
}
