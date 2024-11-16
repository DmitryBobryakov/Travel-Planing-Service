package org.tps.personal;

import org.tps.personal.controllers.FriendController;
import org.tps.personal.controllers.ProfileController;
import org.tps.personal.services.FriendService;
import org.tps.personal.services.UserService;

import static spark.Spark.get;
import static spark.Spark.post;

public class PersonalModule {

  public static void main(String[] args) {
    UserService userService = new UserService();
    FriendService friendService = new FriendService(userService);

    ProfileController profileController = new ProfileController(userService);
    FriendController friendController = new FriendController(friendService);

    get("/profile/:id", profileController.getProfile);
    get("/friends/:id", friendController.getFriends);
    post("/friends/:id/add", friendController.addFriend);
  }
}
