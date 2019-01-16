package bjj.request;

import java.util.UUID;

public class MoveRequest {

    private final String name;
    private final String description;

    public MoveRequest(String name, String description) {
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
