package org.tps.personal;

import org.tps.personal.controller.*;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/public");

        UsersController usersController = new UsersController();
        FriendsController friendsController = new FriendsController();

        usersController.init();
        friendsController.init();
    }
}
