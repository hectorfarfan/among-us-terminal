import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nave_espacial";
    private static final String USER = "root";
    private static final String PASS = "root";

    // Método público y estático (el '+' en UML indica public)
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la BBDD: " + e.getMessage());
        }
    }
}
