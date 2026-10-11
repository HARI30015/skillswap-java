import java.sql.Connection;
import java.sql.Statement;

/**
 * Creates the tables automatically on first run,
 * so there is nothing to set up by hand.
 */
public class DBInit {

    public static void ensureTables() {
        String stdTable =
            "CREATE TABLE IF NOT EXISTS std (" +
            "  id      INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  name    TEXT NOT NULL UNIQUE," +
            "  pass    TEXT NOT NULL," +
            "  contact TEXT)";
        String skillsTable =
            "CREATE TABLE IF NOT EXISTS skills (" +
            "  skill_id    INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  std_id      INTEGER NOT NULL," +
            "  teach_skill TEXT," +
            "  learn_skill TEXT," +
            "  FOREIGN KEY (std_id) REFERENCES std(id))";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {
            st.execute(stdTable);
            st.execute(skillsTable);
        } catch (Exception e) {
            System.out.println("Could not initialise database: " + e.getMessage());
        }
    }
}
