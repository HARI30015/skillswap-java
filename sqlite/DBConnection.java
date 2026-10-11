import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * SQLite database connection - zero setup version of SkillSwap.
 *
 * No Oracle server needed. The database is a single file
 * (skillswap.db) created automatically in the folder you run from.
 * Just keep sqlite-jdbc.jar on the classpath.
 */
public class DBConnection {

    // Database file (created automatically on first run)
    private static final String DB_URL = "jdbc:sqlite:skillswap.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found. Add sqlite-jdbc.jar to the classpath.");
        }
    }

    /** Opens a connection to the local SQLite database file. */
    public static Connection getConnection() throws Exception {
        Connection con = DriverManager.getConnection(DB_URL);
        try (Statement st = con.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return con;
    }
}
