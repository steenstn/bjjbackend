package bjj.domain;

import java.time.LocalDate;
import java.util.UUID;

public class TrainingSession {

    private final UUID id;
    private final TrainingType trainingType;
    private final LocalDate date;
    private final int lengthMin;

    public TrainingSession(UUID id, TrainingType trainingType, LocalDate date, int lengthMin) {

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

    public LocalDate getDate() {
        return date;
    }

    public int getLengthMin() {
        return lengthMin;
    }
}
