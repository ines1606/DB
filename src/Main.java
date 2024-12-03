import javax.swing.*;
public class Main {

    private JFrame mainFrame;
    private LoginPage loginPage;
    private QueriesPage queriesPage;
    private boolean isOnLoginPage = true;

    public Main() {
        // Initialize the main frame
        mainFrame = new JFrame("Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        // Initialize pages
        loginPage = new LoginPage();
        queriesPage = new QueriesPage("User"); // Placeholder for user name

        // Setup the main frame with the initial login page
        mainFrame.setContentPane(loginPage.getMainPanel());
        mainFrame.pack();
        mainFrame.setVisible(true);

        loginPage.setLoginSuccessListener(() -> {
            if (loginPage.checkAndResetLoginStatus()) {
                togglePage();
            }
        });
    }


    private void togglePage() {
        if (isOnLoginPage) {
            // Transitioning from LoginPage to QueriesPage
            queriesPage.updateUser(loginPage.getUsername());
            mainFrame.setContentPane(queriesPage.getMainPanel());
        } else {
            // Transitioning back to LoginPage if needed later
            mainFrame.setContentPane(loginPage.getMainPanel());
        }
        mainFrame.pack();
        isOnLoginPage = !isOnLoginPage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}