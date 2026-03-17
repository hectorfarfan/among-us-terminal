import java.sql.*;
import java.util.ArrayList;

public class SalaDAOImpl implements SalaDAO {

    @Override
    public void insertar(Sala sala) {
        String sql = "INSERT INTO sala (nombre, saboteada) VALUES (?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, sala.getNombre());
            ps.setBoolean(2, sala.isSaboteada());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                sala.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar sala: " + e.getMessage());
        }
    }

    @Override
    public Sala obtener(int id) {
        String sql = "SELECT * FROM sala WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return construirSala(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener sala: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Sala> obtenerTodos() {
        ArrayList<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM sala";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                salas.add(construirSala(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener salas: " + e.getMessage());
        }
        return salas;
    }

    @Override
    public void actualizar(Sala sala) {
        String sql = "UPDATE sala SET nombre = ?, saboteada = ? WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sala.getNombre());
            ps.setBoolean(2, sala.isSaboteada());
            ps.setInt(3, sala.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar sala: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM sala WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar sala: " + e.getMessage());
        }
    }

    private Sala construirSala(ResultSet rs) throws SQLException {
        Sala sala = new Sala(rs.getString("nombre"));
        sala.setId(rs.getInt("id"));
        sala.setSaboteada(rs.getBoolean("saboteada"));
        return sala;
    }
}