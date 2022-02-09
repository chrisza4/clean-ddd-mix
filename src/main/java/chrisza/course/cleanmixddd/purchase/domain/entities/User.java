package chrisza.course.cleanmixddd.purchase.domain.entities;

import chrisza.course.cleanmixddd.purchase.domain.valueobjects.PermissionLevel;

public class User {
    private long id;
    private String username;
    private PermissionLevel level;

    public User(int id, String username, PermissionLevel level) {
        this.username = username;
        this.id = id;
        this.level = level;
    }

    public PermissionLevel getLevel() {
        return level;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return this.id;
    }
}
