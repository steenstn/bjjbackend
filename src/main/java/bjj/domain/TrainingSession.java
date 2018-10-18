package bjj.domain;

import java.util.Date;
import java.util.UUID;

public class TrainingSession {

    private final UUID id;
    private final TrainingType trainingType;
    private final Date date;
    private final int lengthMin;

    public TrainingSession(UUID id, TrainingType trainingType, Date date, int lengthMin) {

        this.id = id;
        this.trainingType = trainingType;
        this.date = date;
        this.lengthMin = lengthMin;
    }

    public UUID getId() {
        return id;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public Date getDate() {
        return date;
    }

    public int getLengthMin() {
        return lengthMin;
    }
}
