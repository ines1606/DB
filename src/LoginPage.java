import javax.swing.*;
import java.awt.*;

public class LoginPage {
    private JPanel mainPanel; // Representing the root panel linked with the .form file
    private JLabel TitleLabel;
    private JTextField UserField;
    private JLabel UserLabel;
    private JLabel PasswordLabel;
    private JPasswordField passwordInput;
    private JButton PasswordToggle;
    private JLabel errorLabel;
    private JButton loginButton;

    private final String correctUsername = "user";
    private final String correctPassword = "password";

    private boolean isPasswordVisible = false; // Flag to track password visibility


    // Assuming these images are in the 'src' directory
    private final ImageIcon openEyeIcon = new ImageIcon(getClass().getResource("/eye_open.png"));
    private final ImageIcon closedEyeIcon = new ImageIcon(getClass().getResource("/eye_closed.png"));

    public LoginPage() {
        UserField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        passwordInput.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        PasswordToggle.setIcon(openEyeIcon);
        errorLabel.setVisible(false);
        // Setup action listeners
        PasswordToggle.addActionListener(e -> {
            if (isPasswordVisible) {
                passwordInput.setEchoChar('\u2022'); // Hide password
                PasswordToggle.setIcon(closedEyeIcon); // Set closed eye icon
            } else {
                passwordInput.setEchoChar('\0'); // Show password
                PasswordToggle.setIcon(openEyeIcon); // Set open eye icon
            }
            isPasswordVisible = !isPasswordVisible; // Toggle the password visibility state
        });

        loginButton.addActionListener(e -> {
            String enteredUsername = UserField.getText();
            String enteredPassword = new String(passwordInput.getPassword());

            if (enteredUsername.equals(correctUsername) && enteredPassword.equals(correctPassword)) {
                JOptionPane.showMessageDialog(null, "Login successful!"); // Corrected frame reference
                errorLabel.setVisible(false);
            } else {
                errorLabel.setText("Invalid username or password");
                errorLabel.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // Runnable to ensure you are running on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login Page");
            LoginPage loginPage = new LoginPage();
            frame.setContentPane(loginPage.mainPanel); // Use the main panel linked in the .form

            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); // Adjusts the window to its components
            frame.setVisible(true);
        });
    }
}