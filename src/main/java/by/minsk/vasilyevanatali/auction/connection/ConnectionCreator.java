package by.minsk.vasilyevanatali.auction.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionCreator {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionCreator.class);
    private static final String DATABASE_PROPERTY = "database";
    private static final String DATABASE_URL = "url";
    private static final String DATABASE_USER = "user";
    private static final String DATABASE_PASSWORD = "password";
    private static ResourceBundle resource = ResourceBundle.getBundle(DATABASE_PROPERTY);

    public static Connection createConnection() throws ClassNotFoundException, SQLException {

        String url = resource.getString(DATABASE_URL);
        String user = resource.getString(DATABASE_USER);
        String password = resource.getString(DATABASE_PASSWORD);
        Class.forName(resource.getString("driver"));
        return DriverManager.getConnection(url, user, password);

    }

    public static int getPollSize() {
        return Integer.parseInt(resource.getString("poolSize"));
    }
}
