package bjj.database;

import bjj.domain.TrainingSession;
import bjj.input.TrainingSessionInput;

import java.util.List;

public interface TrainingSessionRepository {

    boolean insertTrainingSession(TrainingSessionInput trainingSessionInput);
    List<TrainingSession> getAllTrainingSessions();

}
