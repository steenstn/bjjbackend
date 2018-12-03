package bjj.request;

import java.util.UUID;

public class MoveRequest {

    private final String name;
    private final String description;

    public MoveRequest(UUID id, String name, String description, UUID userId) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

}
