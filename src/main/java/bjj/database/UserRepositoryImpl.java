package bjj.database;

import bjj.domain.User;
import bjj.request.UserRegistrationRequest;
import bjj.security.JwtTokenProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.UUID;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private DatabaseConnection dbConnection;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public UserRepositoryImpl(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public User registerUser(String username, String password) {
        try {
            Connection connection = dbConnection.getConnection();
            String query = "insert into users (id, username, password)"
                    + "values(?,?,?)";
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            User dbUser = new User(UUID.randomUUID(), username.toLowerCase(), hashedPassword);

            PreparedStatement s = connection.prepareStatement(query);
            s.setObject(1, dbUser.getId());
            s.setString(2, dbUser.getUsername());
            s.setString(3, dbUser.getPassword());

            int result = s.executeUpdate();
            if(result > 0) {
                return dbUser;
            }
            else {
                throw new RuntimeException("Could not register user");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean userExists(String username) {
        try {
        Connection connection = dbConnection.getConnection();

        String query = "select * from users where username = ?";
        PreparedStatement s = connection.prepareStatement(query);
        s.setString(1, username.toLowerCase());

        ResultSet resultSet = s.executeQuery();
        return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public User getUser(String username) {
        try {
            Connection connection = dbConnection.getConnection();

            String query = "select * from users where username = ?";
            PreparedStatement s = connection.prepareStatement(query);

            s.setString(1, username.toLowerCase());
            ResultSet resultSet = s.executeQuery();

            if(resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String dbUsername = resultSet.getString("username");
                String dbPassword = resultSet.getString("password");
                return new User(id, dbUsername, dbPassword);
            }

            throw new RuntimeException("No user found");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
