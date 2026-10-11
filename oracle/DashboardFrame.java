import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Main window shown after login.
 * Tabs: Add Skill | View Students | Search Skill
 */
public class DashboardFrame extends JFrame {

    private final int stdId;
    private final String userName;

    public DashboardFrame(int stdId, String userName) {
        super("SkillSwap - Dashboard");
        this.stdId = stdId;
        this.userName = userName;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 460);
        setLocationRelativeTo(null);

        JLabel welcome = new JLabel("Welcome, " + userName + "!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        welcome.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(welcome, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Add Skill", buildAddSkillPanel());
        tabs.addTab("View Students", buildViewPanel());
        tabs.addTab("Search Skill", buildSearchPanel());
        add(tabs, BorderLayout.CENTER);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(logoutBtn);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ---------------- Add Skill ----------------
    private JPanel buildAddSkillPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        JTextField teachField = new JTextField(20);
        JTextField learnField = new JTextField(20);

        g.gridx = 0; g.gridy = 0;
        p.add(new JLabel("Skill you can teach:"), g);
        g.gridx = 1;
        p.add(teachField, g);

        g.gridx = 0; g.gridy = 1;
        p.add(new JLabel("Skill you want to learn:"), g);
        g.gridx = 1;
        p.add(learnField, g);

        JButton saveBtn = new JButton("Save");
        g.gridx = 0; g.gridy = 2; g.gridwidth = 2;
        p.add(saveBtn, g);

        saveBtn.addActionListener(e -> {
            String teach = teachField.getText().trim();
            String learn = learnField.getText().trim();
            if (teach.isEmpty() && learn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter at least one skill.",
                        "Missing input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String sql = "INSERT INTO skills (std_id, teach_skill, learn_skill) VALUES (?, ?, ?)";
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, stdId);
                ps.setString(2, teach.isEmpty() ? null : teach);
                ps.setString(3, learn.isEmpty() ? null : learn);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Skill saved!",
                        "Done", JOptionPane.INFORMATION_MESSAGE);
                teachField.setText("");
                learnField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return p;
    }

    // ---------------- View Students ----------------
    private JPanel buildViewPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Name", "Contact", "Can Teach", "Wants to Learn"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadStudents(model, null));
        p.add(refreshBtn, BorderLayout.SOUTH);

        loadStudents(model, null); // load on open
        return p;
    }

    // ---------------- Search Skill ----------------
    private JPanel buildSearchPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel top = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        top.add(new JLabel("Skill:"));
        top.add(searchField);
        top.add(searchBtn);
        p.add(top, BorderLayout.NORTH);

        String[] cols = {"Name", "Contact", "Can Teach", "Wants to Learn"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        p.add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> {
            String skill = searchField.getText().trim();
            if (skill.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a skill to search.",
                        "Missing input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            loadStudents(model, skill);
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No student teaches this skill.",
                        "No results", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return p;
    }

    /**
     * Fills the table. If skillFilter is null, loads everyone;
     * otherwise only students whose teach_skill matches (case-insensitive).
     */
    private void loadStudents(DefaultTableModel model, String skillFilter) {
        model.setRowCount(0); // clear
        String sql = "SELECT s.name, s.contact, k.teach_skill, k.learn_skill " +
                     "FROM std s LEFT JOIN skills k ON k.std_id = s.id";
        if (skillFilter != null) {
            sql += " WHERE UPPER(k.teach_skill) = UPPER(?)";
        }
        sql += " ORDER BY s.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (skillFilter != null) {
                ps.setString(1, skillFilter);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("teach_skill"),
                    rs.getString("learn_skill")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
