package bjj.database;

import bjj.domain.TrainingSession;
import bjj.domain.TrainingType;
import bjj.request.TrainingSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TrainingSessionRepositoryImpl implements TrainingSessionRepository{

    @Autowired
    private DatabaseConnection dbConnection;

    public TrainingSessionRepositoryImpl(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    @Override
    public boolean insertTrainingSession(TrainingSessionRequest trainingSessionRequest) {
        try {
            Connection connection = dbConnection.getConnection();
            String query = "insert into training_session (id, training_type, training_date, training_length_min)"
                    + "values(?,?,?,?)";
            PreparedStatement s = connection.prepareStatement(query);
            s.setObject(1, UUID.randomUUID());
            s.setString(2, trainingSessionRequest.getTrainingType().toString());
            s.setDate(3, Date.valueOf(trainingSessionRequest.getDate()));
            s.setInt(4, trainingSessionRequest.getLengthMin());

            int result = s.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TrainingSession> getAllTrainingSessions() {
        try {

            Connection connection = dbConnection.getConnection();

            Statement s = connection.createStatement();
            ResultSet resultSet = s.executeQuery("select * from training_session");
            resultSet.next();
            List<TrainingSession> result = new ArrayList<>();
            while(!resultSet.isAfterLast()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                TrainingType type = TrainingType.valueOf(resultSet.getString("training_type").toUpperCase());
                LocalDate date = resultSet.getDate("training_date").toLocalDate();
                int length = resultSet.getInt("training_length_min");
                result.add(new TrainingSession(id,type,date,length));
                resultSet.next();
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
