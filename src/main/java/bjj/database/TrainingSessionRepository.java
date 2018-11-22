package bjj.database;

import bjj.domain.TrainingSession;
import bjj.domain.User;
import bjj.request.TrainingSessionRequest;

import java.util.List;
import java.util.UUID;

public interface TrainingSessionRepository {

    boolean insertTrainingSession(TrainingSessionRequest trainingSessionRequest, User user);
    List<TrainingSession> getTrainingSessionsForUser(User user);
    boolean editTrainingSession(TrainingSessionRequest trainingSessionRequest, UUID id);
}
