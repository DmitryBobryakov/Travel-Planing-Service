package org.tps.personal.controllers;

import org.tps.personal.models.User;
import org.tps.personal.services.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ProfileController {

  private UserService userService;

  public ProfileController(UserService userService) {
    this.userService = userService;
  }

  public Route getProfile = (Request request, Response response) -> {
    int userId = Integer.parseInt(request.params(":id"));
    User user = userService.getUserById(userId);
    if (user != null) {
      return "Profile: " + user.getUsername() + ", Email: " + user.getEmail();
    } else {
      response.status(404);
      return "User not found";
    }
  };
}
