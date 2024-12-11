import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private ConfigManager configManager;
    private String user;

    public DatabaseManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    // Authenticate user and establish a connection
    public Connection authenticateUser(String username, String password) throws SQLException {
        String dbHost = configManager.getProperty("db.host");
        String dbPort = configManager.getProperty("db.port");
        String dbName = configManager.getProperty("db.name");

        // Construct the database URL
        String dbUrl = String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName);

        // Establish a connection using the provided user credentials
        this.connection = DriverManager.getConnection(dbUrl, username, password);
        this.user = username;
        return this.connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public String getUser() {
        return user;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
