import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.Toolkit;


import java.util.function.Consumer;

public class LoginPage {

    private DatabaseManager dbManager;

    private JPanel mainPanel; // Representing the root panel linked with the .form file
    private JLabel TitleLabel;
    private JTextField UserField;
    private JLabel UserLabel;
    private JLabel PasswordLabel;
    private JPasswordField passwordInput;
    private JButton PasswordToggle;
    private JLabel errorLabel;
    private JButton loginButton;
    private boolean onLoginSuccess;
    private Runnable loginSuccessListener;

    private final String correctUsername = "user";
    private final String correctPassword = "password";

    private boolean isPasswordVisible = false; // Flag to track password visibility


    // Assuming these images are in the 'src' directory
    private final ImageIcon openEyeIcon = new ImageIcon(getClass().getResource("/eye_open.png"));
    private final ImageIcon closedEyeIcon = new ImageIcon(getClass().getResource("/eye_closed.png"));

    public LoginPage(DatabaseManager dbManager) {

        UserField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        passwordInput.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        PasswordToggle.setIcon(closedEyeIcon);
        errorLabel.setVisible(false);
        // Setup action listeners
        PasswordToggle.addActionListener(e -> {
            if (isPasswordVisible) {
                passwordInput.setEchoChar('\u2022');
                PasswordToggle.setIcon(closedEyeIcon);
            } else {
                passwordInput.setEchoChar('\0');
                PasswordToggle.setIcon(openEyeIcon);
            }
            isPasswordVisible = !isPasswordVisible;
        });

        loginButton.addActionListener(e -> {
            String enteredUsername = UserField.getText();
            String enteredPassword = new String(passwordInput.getPassword());

            try {
                // Authenticate the user and establish a connection
                Connection userConnection = dbManager.authenticateUser(enteredUsername, enteredPassword);

                // On successful login
                if (userConnection != null) {
                    errorLabel.setVisible(false);
                    onLoginSuccess = true;
                    onLoginSuccess(); // Notify Main class or other listeners
                }
            } catch (SQLException ex) {
                errorLabel.setText("Invalid username or password, or insufficient permissions.");
                errorLabel.setVisible(true);
                Toolkit.getDefaultToolkit().beep();
                ex.printStackTrace();
            }
        });
    }

    public void resetPage(){
        UserField.setText("");
        passwordInput.setText("");
        errorLabel.setVisible(false);
        onLoginSuccess = false;
    }

    private void onLoginSuccess() {
        if (loginSuccessListener != null) {
            loginSuccessListener.run();
        }
    }

    public void setLoginSuccessListener(Runnable loginSuccessListener) {
        this.loginSuccessListener = loginSuccessListener;
    }

    public boolean checkAndResetLoginStatus() {
        if (onLoginSuccess) {
            onLoginSuccess = false; // Reset después de ser capturado
            return true;
        }
        return false;
    }

    public String getUsername() {
        return UserField.getText();
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

}