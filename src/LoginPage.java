import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

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
    private boolean onLoginSuccess;
    private Runnable loginSuccessListener;

    private final String correctUsername = "user";
    private final String correctPassword = "password";

    private boolean isPasswordVisible = false; // Flag to track password visibility


    // Assuming these images are in the 'src' directory
    private final ImageIcon openEyeIcon = new ImageIcon(getClass().getResource("/eye_open.png"));
    private final ImageIcon closedEyeIcon = new ImageIcon(getClass().getResource("/eye_closed.png"));

    public LoginPage() {
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

            if (enteredUsername.equals(correctUsername) && enteredPassword.equals(correctPassword)) {
                errorLabel.setVisible(false);
                onLoginSuccess = true;
                onLoginSuccess();
            } else {
                errorLabel.setText("Invalid username or password");
                errorLabel.setVisible(true);
                onLoginSuccess = false;


            }
        });
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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login Page");
            LoginPage loginPage = new LoginPage();
            frame.setContentPane(loginPage.mainPanel);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}