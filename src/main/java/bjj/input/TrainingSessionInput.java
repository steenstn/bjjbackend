package bjj.input;

import bjj.domain.TrainingType;
import java.time.*;

public class TrainingSessionInput {

    private final TrainingType trainingType;
    private final LocalDate date;
    private final int lengthMin;

    public TrainingSessionInput(TrainingType trainingType, LocalDate date, int lengthMin) {
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
