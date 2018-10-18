package bjj.database;

import bjj.domain.TrainingSession;
import bjj.domain.TrainingType;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.Data;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DatabaseConnection {

    public DatabaseConnection() {

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
                Date date = resultSet.getDate("training_date");
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
