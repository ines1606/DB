import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Map;

public class QueriesPage {
    private JLabel greetingLabel;
    private JTextPane outputPlane;
    private JButton cleanButton;
    private JButton runButton;
    private JComboBox<String> querySelector;
    private JPanel mainPanel;
    private JLabel queryLabel;
    private JButton LogoutButton;
    private JTable resultsTable;
    private JScrollPane scrollPane;
    private String currentQuery;
    private boolean logout = false;
    private Runnable logoutListener;
    // Placeholder for the specific user, assuming it's passed or set somewhere

    private Connection userConnection; // Store the logged-in user's connection

    public QueriesPage(Connection userConnection, String user, Map<String, String> queries, Map<String, String> userFriendlyNames) {
        this.userConnection = userConnection;
        greetingLabel.setText("Hello, " + user + "!");

        for (Map.Entry<String, String> entry : queries.entrySet()) {
            String propertyKey = entry.getKey();
            String friendlyName = userFriendlyNames.getOrDefault(propertyKey, propertyKey);
            querySelector.addItem(friendlyName);
        }

        querySelector.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        runButton.addActionListener(e -> {
            String selectedFriendlyName = (String) querySelector.getSelectedItem();
            String selectedQueryKey = userFriendlyNames.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().equals(selectedFriendlyName))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            if (selectedQueryKey != null) {
                String query = queries.get(selectedQueryKey);
                executeAndDisplayQuery(query);
            }
        });

        cleanButton.addActionListener(e -> {
            resultsTable.setModel(new DefaultTableModel()); // Clear output pane
        });

        LogoutButton.addActionListener(e -> {
            logout = true;
            onLogout();
        });
    }

    private void executeAndDisplayQuery(String query) {
        try (Statement statement = userConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            // Process and display results as before
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Initialize model with column names
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

            resultsTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onLogout() {
        // Close the database connection
        if (userConnection != null) {
            try {
                userConnection.close();
                System.out.println("User connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Notify the main application to switch back to the login screen
        if (logoutListener != null) {
            logoutListener.run();
        }
    }

    public void setLogoutListener(Runnable logoutListener) {
        this.logoutListener = logoutListener;
    }

    public boolean isLogoutTriggered() {
        if (logout) {
            logout = false;
            return true;
        }
        return false;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
