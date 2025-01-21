package org.tps.personal.service;

import org.tps.personal.model.Friend;
import java.util.ArrayList;
import java.util.List;

public class FriendsService {
    private List<Friend> friends = new ArrayList<>();

    public FriendsService() {
        friends.add(new Friend("1", "2"));
        friends.add(new Friend("2", "1"));
    }

    public List<Friend> getFriends(String userId) {
        List<Friend> usersFriends = new ArrayList<>();
        for (Friend friend : friends) {
            if (friend.getUserId().equals(userId)) {
                usersFriends.add(friend);
            }
        }
        return usersFriends;
    }
}
