package bjj.database;

import bjj.domain.User;
import bjj.request.UserRegistrationRequest;
import bjj.security.JwtTokenProvider;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbConnection.getConnection();
            String query = "insert into users (id, username, password)"
                    + "values(?,?,?)";
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            User dbUser = new User(UUID.randomUUID(), username.toLowerCase(), hashedPassword);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, dbUser.getId());
            preparedStatement.setString(2, dbUser.getUsername());
            preparedStatement.setString(3, dbUser.getPassword());

            int result = preparedStatement.executeUpdate();
            if(result > 0) {
                return dbUser;
            }
            else {
                throw new RuntimeException("Could not register user");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean userExists(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        connection = dbConnection.getConnection();

        String query = "select * from users where username = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username.toLowerCase());

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public User getUser(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnection.getConnection();

            String query = "select * from users where username = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username.toLowerCase());
            resultSet = preparedStatement.executeQuery();

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
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
