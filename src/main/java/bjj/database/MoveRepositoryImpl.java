package bjj.database;

import bjj.domain.Move;
import bjj.domain.User;
import bjj.request.MoveRequest;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MoveRepositoryImpl implements MoveRepository {

    private DatabaseConnection dbConnection;

    public MoveRepositoryImpl(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Move insertMove(MoveRequest move, User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Move resultingMove = new Move(UUID.randomUUID(), move.getName(), move.getDescription());

            connection = dbConnection.getConnection();
            String query = "insert into moves (id, user_id, name, description)"
                    + "values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, resultingMove.getId());
            preparedStatement.setObject(2, user.getId());
            preparedStatement.setString(3, resultingMove.getName());
            preparedStatement.setString(4, resultingMove.getDescription());

            int result = preparedStatement.executeUpdate();
            if(result > 0) {
                return resultingMove;
            }
            throw new RuntimeException("Failed to insert move");
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
    public List<Move> getMovesForUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnection.getConnection();
            String query = "select * from moves where user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, user.getId());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()) {
                return new ArrayList<>();
            }
            resultSet.next();
            List<Move> result = new ArrayList<>();
            while (!resultSet.isAfterLast()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String name= resultSet.getString("name");
                String description= resultSet.getString("description");
                result.add(new Move(id, name, description));
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
