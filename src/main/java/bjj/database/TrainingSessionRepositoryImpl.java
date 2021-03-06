package bjj.database;

import bjj.domain.TrainingSession;
import bjj.domain.TrainingType;
import bjj.domain.User;
import bjj.request.TrainingSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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
    public TrainingSession insertTrainingSession(TrainingSessionRequest trainingSessionRequest, User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            TrainingSession resultingTrainingSession = new TrainingSession(UUID.randomUUID(),
                    trainingSessionRequest.getTrainingType(), trainingSessionRequest.getDate(), trainingSessionRequest.getLengthMin());

            connection = dbConnection.getConnection();
            String query = "insert into training_session (id, user_id, training_type, training_date, training_length_min)"
                    + "values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, resultingTrainingSession.getId());
            preparedStatement.setObject(2, user.getId());
            preparedStatement.setString(3, resultingTrainingSession.getTrainingType().toString());
            preparedStatement.setDate(4, Date.valueOf(resultingTrainingSession.getDate()));
            preparedStatement.setInt(5, resultingTrainingSession.getLengthMin());

            int result = preparedStatement.executeUpdate();

            return resultingTrainingSession;

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

    public boolean editTrainingSession(TrainingSessionRequest trainingSessionRequest, UUID id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbConnection.getConnection();
            String query = "update training_session set training_type = ?, training_date = ?, training_length_min = ?" +
                    "WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, trainingSessionRequest.getTrainingType().toString().toUpperCase());
            preparedStatement.setDate(2, Date.valueOf(trainingSessionRequest.getDate()));
            preparedStatement.setInt(3, trainingSessionRequest.getLengthMin());
            preparedStatement.setObject(4,id);

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
    public boolean deleteTrainingSession(UUID id, User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbConnection.getConnection();
            String query = "delete from training_session where id = ? and user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, user.getId());


            int result = preparedStatement.executeUpdate();
            return result > 0;

        }catch (SQLException e) {
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

            if(!resultSet.next()) {
                return new ArrayList<>();
            }
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
