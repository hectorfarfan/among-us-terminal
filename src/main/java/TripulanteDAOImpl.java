import java.sql.*;
import java.util.ArrayList;

public class TripulanteDAOImpl implements TripulanteDAO {

    @Override
    public void insertar(Tripulante tripulante) {
        String sql = "INSERT INTO tripulante (nombre, rol, vivo) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tripulante.getNombre());
            ps.setString(2, tripulante.getRol());
            ps.setBoolean(3, tripulante.isVivo());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                tripulante.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar tripulante: " + e.getMessage());
        }
    }

    @Override
    public Tripulante obtener(int id) {
        String sql = "SELECT * FROM tripulante WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return construirTripulante(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener tripulante: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Tripulante> obtenerTodos() {
        ArrayList<Tripulante> lista = new ArrayList<>();
        String sql = "SELECT * FROM tripulante";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(construirTripulante(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener tripulantes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Tripulante tripulante) {
        String sql = "UPDATE tripulante SET nombre = ?, rol = ?, vivo = ? WHERE id = ?";
        try (Connection con = DBUtil.getInstance().getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tripulante.getNombre());
            ps.setString(2, tripulante.getRol());
            ps.setBoolean(3, tripulante.isVivo());
            ps.setInt(4, tripulante.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar tripulante: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tripulante WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar tripulante: " + e.getMessage());
        }
    }

    private Tripulante construirTripulante(ResultSet rs) throws SQLException {
        String nombre = rs.getString("nombre");
        String rol = rs.getString("rol");
        boolean vivo = rs.getBoolean("vivo");

        Tripulante tripulante = switch (rol) {
            case "ingeniero" -> new Ingeniero(nombre);
            case "medico"    -> new Medico(nombre);
            case "capitan"   -> new Capitan(nombre);
            case "impostor"  -> new Impostor(nombre);
            default -> throw new SQLException("Rol desconocido: " + rol);
        };

        tripulante.setId(rs.getInt("id"));
        tripulante.setVivo(vivo);
        return tripulante;
    }
}