import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueriesPage {
    private JLabel greetingLabel;
    private JTextPane outputPlane;
    private JButton cleanButton;
    private JButton runButton;
    private JTextField queriesField;
    private JPanel mainPanel;
    private JLabel queryLabel;
    private JButton LogoutButton;
    private String currentQuery;
    private String queryOutput = "Sample Output"; // Placeholder for output results
    private boolean logout = false;
    private Runnable logoutListener;
    // Placeholder for the specific user, assuming it's passed or set somewhere


    public QueriesPage(String username) {
        queriesField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuery = queriesField.getText(); // Get query input
                // Here, you would add logic to execute the query and update queryOutput accordingly
                outputPlane.setText(queryOutput); // Display placeholder output
            }
        });

        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputPlane.setText(""); // Clear output pane
            }
        });
        LogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout = true;
                onLogout();
            }
        });
    }

    private void onLogout() {
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



    public void updateUser(String username) {
        greetingLabel.setText("Hello, " + username + "!");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String enteredUsername = "Fata Morgana";
            JFrame frame = new JFrame("Queries Page");
            QueriesPage queriesPage = new QueriesPage(enteredUsername);
            frame.setContentPane(queriesPage.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
