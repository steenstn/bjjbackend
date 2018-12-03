package bjj.domain;

import java.util.UUID;

public class Move {
    private final UUID id;
    private final String name;
    private final String description;

    public Move(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
