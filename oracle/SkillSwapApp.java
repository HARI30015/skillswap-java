import javax.swing.*;

/**
 * Entry point. Launches the Swing login screen.
 */
public class SkillSwapApp {
    public static void main(String[] args) {
        // Swing windows must be created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
