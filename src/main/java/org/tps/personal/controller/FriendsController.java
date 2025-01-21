package org.tps.personal.controller;

import com.google.gson.Gson;
import org.tps.personal.model.Friend;
import org.tps.personal.service.FriendsService;
import static spark.Spark.*;

import java.util.List;

public class FriendsController {
    private final FriendsService friendsService = new FriendsService();
    private final Gson gson = new Gson();

    public void init() {
        get("/friends/:userId", (req, res) -> {
            String userId = req.params(":userId");
            List<Friend> friends = friendsService.getFriends(userId);
            return gson.toJson(friends);
        });
    }
}
