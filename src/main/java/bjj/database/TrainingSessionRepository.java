package bjj.database;

import bjj.domain.TrainingSession;
import bjj.domain.User;
import bjj.request.TrainingSessionRequest;

import java.util.List;

public interface TrainingSessionRepository {

    boolean insertTrainingSession(TrainingSessionRequest trainingSessionRequest, User user);
    List<TrainingSession> getAllTrainingSessions();
    List<TrainingSession> getTrainingSessionsForUser(User user);

}
