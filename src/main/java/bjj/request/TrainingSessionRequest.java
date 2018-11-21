package bjj.request;

import bjj.domain.TrainingType;
import java.time.*;

public class TrainingSessionRequest {

    private final TrainingType trainingType;
    private final LocalDate date;
    private final int lengthMin;

    public TrainingSessionRequest(TrainingType trainingType, LocalDate date, int lengthMin, Object id) {
        this.trainingType = trainingType;
        this.date = date;
        this.lengthMin = lengthMin;
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
