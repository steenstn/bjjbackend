package bjj.database;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

@Component
class DatabaseConnection {

    DatabaseConnection() {

    }

    Connection getConnection() {

        if (System.getenv("DATABASE_URL") == null) {
            String username = "sa";
            String password = "";
            String dbURL = "jdbc:h2:mem:testdb";

            try {
                return DriverManager.getConnection(dbURL, username, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        try {
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
