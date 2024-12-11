import javax.swing.*;
import java.sql.*;
import java.io.IOException;
import java.util.Map;

public class Main {
    private JFrame mainFrame;
    private LoginPage loginPage;
    private QueriesPage queriesPage;
    private DatabaseManager dbManager;

    public Main() {

        try {
            ConfigManager configManager = new ConfigManager("db_config.properties", "thomas"); // Load configuration
            dbManager = new DatabaseManager(configManager); // Initialize DatabaseManager
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load configuration: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Initialize the main frame
        mainFrame = new JFrame("Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        showLoginScreen();
    }

    private void showLoginScreen() {
        loginPage = new LoginPage(dbManager);
        loginPage.setLoginSuccessListener(() -> {
            try {
                QueryManager queryManager = new QueryManager("queries.properties");
                Map<String, String> queries = queryManager.getQueries();
                Map<String, String> userFriendlyNames = queryManager.getUserFriendlyNames();

                Connection userConnection = dbManager.getConnection();
                String username = loginPage.getUsername();

                showQueriesPage(userConnection, username, queries, userFriendlyNames);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(mainFrame, "Failed to load queries: " + e.getMessage());
            }
        });

        mainFrame.setContentPane(loginPage.getMainPanel());
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void showQueriesPage(Connection userConnection, String username, Map<String, String> queries, Map<String, String> userFriendlyNames) {
        queriesPage = new QueriesPage(userConnection, username, queries, userFriendlyNames);
        queriesPage.setLogoutListener(this::showLoginScreen);

        mainFrame.setContentPane(queriesPage.getMainPanel());
        mainFrame.pack();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
