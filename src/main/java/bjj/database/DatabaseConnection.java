package bjj.database;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

@Component
class DatabaseConnection {

    DatabaseConnection() {

    }

    Connection getConnection() throws URISyntaxException, SQLException {

        if (System.getenv("DATABASE_URL") == null) {
            String username = "sa";
            String password = "";
            String dbURL = "jdbc:h2:mem:testdb";

            return DriverManager.getConnection(dbURL, username, password);

        }

        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
