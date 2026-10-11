import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Central place for the Oracle database connection.
 *
 * SETUP: change the three values below to match your Oracle setup,
 * then make sure ojdbc11.jar (or ojdbc8.jar) is on the classpath
 * when you compile and run.
 */
public class DBConnection {

    // ---- EDIT THESE FOR YOUR MACHINE ----
    private static final String DB_URL  = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "SYSTEM";       // your Oracle username
    private static final String DB_PASS = "tiger";        // your Oracle password
    // -------------------------------------

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC driver not found. Add ojdbc11.jar to the classpath.");
        }
    }

    /** Opens a fresh connection to the Oracle database. */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
