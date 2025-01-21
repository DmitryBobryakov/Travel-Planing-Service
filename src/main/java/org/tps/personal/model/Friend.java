package org.tps.personal.model;

public class Friend {
    private String userId;
    private String friendId;

    public Friend(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public String getFriendId() {
        return friendId;
    }
}
