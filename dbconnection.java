package pet.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String SCHEMA = "pet_store";
    private static final String USER = "pet_store";
    private static final String PASSWORD = "pet_store";
    private static final String URI = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", HOST, PORT, SCHEMA, USER, PASSWORD);

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URI);
            System.out.println("Connected to the MySQL database: " + URI);
            return connection;
        } catch (SQLException e) {
            throw new dbexception("Unable to get connection at " + URI, e);
        }
    }
}
