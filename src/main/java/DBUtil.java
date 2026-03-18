import java.sql.*;

public class DBUtil {
    // La clase tiene un objeto llamado instancia para reutilizar la conexion creada a posterior
    private static DBUtil instancia;

    // Sirve para crear una sola conexion, lo que ira dentro de la instancia creada antes.
    private Connection conexion;

    private DBUtil(){
        final String DB_URL = "jdbc:mysql://localhost:3306/nave_espacial";
        final String USER = "root";
        final String PASS = "root";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            this.conexion = conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBUtil getInstance(){
        if (instancia == null) {
            instancia = new DBUtil();
        }
        return instancia;
    }

    public Connection getConexion() {
        return this.conexion;
    }



}
