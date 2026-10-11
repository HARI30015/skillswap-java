import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Sign-up screen. Inserts a new row into the std table.
 */
public class SignupFrame extends JFrame {

    private JTextField nameField, contactField;
    private JPasswordField passField, confirmField;

    public SignupFrame() {
        super("SkillSwap - Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Create your SkillSwap account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        form.add(new JLabel("Username:"), gc);
        gc.gridx = 1; gc.weightx = 1.0;
        nameField = new JTextField(15);
        form.add(nameField, gc);

        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0;
        form.add(new JLabel("Password:"), gc);
        gc.gridx = 1; gc.weightx = 1.0;
        passField = new JPasswordField(15);
        form.add(passField, gc);

        gc.gridx = 0; gc.gridy = 2; gc.weightx = 0;
        form.add(new JLabel("Confirm password:"), gc);
        gc.gridx = 1; gc.weightx = 1.0;
        confirmField = new JPasswordField(15);
        form.add(confirmField, gc);

        gc.gridx = 0; gc.gridy = 3; gc.weightx = 0;
        form.add(new JLabel("Contact:"), gc);
        gc.gridx = 1; gc.weightx = 1.0;
        contactField = new JTextField(15);
        form.add(contactField, gc);
        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back to Login");
        buttons.add(registerBtn);
        buttons.add(backBtn);
        add(buttons, BorderLayout.SOUTH);

        registerBtn.addActionListener(e -> doSignup());
        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void doSignup() {
        String name = nameField.getText().trim();
        String pass = new String(passField.getPassword());
        String confirm = new String(confirmField.getPassword());
        String contact = contactField.getText().trim();

        if (name.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password are required.",
                    "Missing input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.",
                    "Signup failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO std (name, pass, contact) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, pass);
            ps.setString(3, contact.isEmpty() ? null : contact);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful! Please login.",
                    "Done", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginFrame();
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            if (msg != null && msg.contains("ORA-00001")) {
                msg = "That username is already taken.";
            }
            JOptionPane.showMessageDialog(this, "Signup failed: " + msg,
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
