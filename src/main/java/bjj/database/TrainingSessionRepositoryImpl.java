package bjj.database;

import bjj.domain.TrainingSession;
import bjj.domain.TrainingType;
import bjj.domain.User;
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
    public boolean insertTrainingSession(TrainingSessionRequest trainingSessionRequest, User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbConnection.getConnection();
            String query = "insert into training_session (id, user_id, training_type, training_date, training_length_min)"
                    + "values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, UUID.randomUUID());
            preparedStatement.setObject(2, user.getId());
            preparedStatement.setString(3, trainingSessionRequest.getTrainingType().toString());
            preparedStatement.setDate(4, Date.valueOf(trainingSessionRequest.getDate()));
            preparedStatement.setInt(5, trainingSessionRequest.getLengthMin());

            int result = preparedStatement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
    }

    @Override
    public List<TrainingSession> getAllTrainingSessions() {
        try (Connection connection = dbConnection.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("select * from training_session")) {

            resultSet.next();
            List<TrainingSession> result = new ArrayList<>();
            while (!resultSet.isAfterLast()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                TrainingType type = TrainingType.valueOf(resultSet.getString("training_type").toUpperCase());
                LocalDate date = resultSet.getDate("training_date").toLocalDate();
                int length = resultSet.getInt("training_length_min");
                result.add(new TrainingSession(id, type, date, length));
                resultSet.next();
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TrainingSession> getTrainingSessionsForUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnection.getConnection();
            String query = "select * from training_session where user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            List<TrainingSession> result = new ArrayList<>();
            while (!resultSet.isAfterLast()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                TrainingType type = TrainingType.valueOf(resultSet.getString("training_type").toUpperCase());
                LocalDate date = resultSet.getDate("training_date").toLocalDate();
                int length = resultSet.getInt("training_length_min");
                result.add(new TrainingSession(id, type, date, length));
                resultSet.next();
            }
            return result;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
    }
}
