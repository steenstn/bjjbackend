package bjj.database;

import bjj.domain.TrainingSession;
import bjj.domain.TrainingType;
import bjj.input.TrainingSessionInput;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseConnection {

    public DatabaseConnection() {

    }

    public boolean insertTrainingSession(TrainingSessionInput trainingSessionInput) {
        try {
            Connection connection = getConnection();
            String query = "insert into training_session (id, training_type, training_date, training_length_min)"
                    + "values(?,?,?,?)";
            PreparedStatement s = connection.prepareStatement(query);
            s.setObject(1,UUID.randomUUID());
            s.setString(2, trainingSessionInput.getTrainingType().toString());
            s.setDate(3,Date.valueOf(trainingSessionInput.getDate()));
            s.setInt(4,trainingSessionInput.getLengthMin());

            int result = s.executeUpdate();

            return result > 0;

        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<TrainingSession> getAllTrainingSessions() {
        try {

            Connection connection = getConnection();

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
        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
