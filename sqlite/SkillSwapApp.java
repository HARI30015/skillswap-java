import javax.swing.*;

/**
 * Entry point. Launches the Swing login screen.
 */
public class SkillSwapApp {
    public static void main(String[] args) {
        DBInit.ensureTables(); // create tables on first run - nothing to set up
        // Swing windows must be created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
