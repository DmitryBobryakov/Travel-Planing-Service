package org.tps.personal.service;

import org.tps.personal.model.User;
import java.util.HashMap;
import java.util.Map;

public class UsersService {
    private Map<String, User> users = new HashMap<>();

    public UsersService() {
        users.put("1", new User("1", "Alice", "alice@example.com"));
        users.put("2", new User("2", "Bob", "bob@example.com"));
    }

    public User getUser(String id) {
        return users.get(id);
    }
}
