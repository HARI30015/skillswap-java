import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Login screen. Verifies the user against the std table.
 * Successful login opens the Dashboard; new users can open the Signup screen.
 */
public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        super("SkillSwap - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 250);
        setLocationRelativeTo(null); // centre on screen
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("SkillSwap Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        form.add(new JLabel("Username:"), gc);
        gc.gridx = 1; gc.weightx = 1.0;
        userField = new JTextField(15);
        form.add(userField, gc);

        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0;
        form.add(new JLabel("Password:"), gc);
        gc.gridx = 1; gc.weightx = 1.0;
        passField = new JPasswordField(15);
        form.add(passField, gc);
        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("New user? Sign Up");
        buttons.add(loginBtn);
        buttons.add(signupBtn);
        add(buttons, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> doLogin());
        signupBtn.addActionListener(e -> {
            dispose();
            new SignupFrame();
        });

        setVisible(true);
    }

    private void doLogin() {
        String name = userField.getText().trim();
        String pass = new String(passField.getPassword());

        if (name.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both username and password.",
                    "Missing input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT id, name FROM std WHERE name = ? AND pass = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String dbName = rs.getString("name");
                dispose();
                new DashboardFrame(id, dbName);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.",
                        "Login failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
