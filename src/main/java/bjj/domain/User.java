package bjj.domain;

import java.util.UUID;

public class User {

    private final UUID id;
    private String username;
    private final String password;

    public User(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UUID getId() {
        return id;
    }
}
