package bjj.database;

import bjj.domain.TrainingSession;
import bjj.request.TrainingSessionRequest;

import java.util.List;

public interface TrainingSessionRepository {

    boolean insertTrainingSession(TrainingSessionRequest trainingSessionRequest);
    List<TrainingSession> getAllTrainingSessions();

}
