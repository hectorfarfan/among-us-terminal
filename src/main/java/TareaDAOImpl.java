import java.sql.*;
import java.util.ArrayList;

public class TareaDAOImpl implements TareaDAO{

    @Override
    public void insertar(Tarea tarea){
        String sqlInsert= "INSERT INTO tarea(descripcion,completada,id_tripulante,id_sala) VALUES (?,?,?,?)";
        Connection con = DBUtil.getInstance().getConnection();

        try (PreparedStatement pst = con.prepareStatement(sqlInsert)){
            pst.setString(1,tarea.getDescripcion());
            pst.setBoolean(2,tarea.isCompletada());
            pst.setInt(3,tarea.getTripulanteAsignado().getId());
            pst.setInt(4,tarea.getSala().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Tarea obtener(int id){
        String sqlRead = "SELECT * FROM tarea WHERE id=?";
        Connection con = DBUtil.getInstance().getConnection();

        TripulanteDAO tripulanteDAO = new TripulanteDAOImpl();
        SalaDAO salaDAO = new SalaDAOImpl();

        try(PreparedStatement pst = con.prepareStatement(sqlRead)){
            pst.setInt(1,id);
            try (ResultSet rs =pst.executeQuery()) {
                if (rs.next()) {
                    int idTarea = rs.getInt("id");
                    String descripcion = rs.getString("descripcion");
                    boolean completada = rs.getBoolean("completada");
                    int tripulanteAsignado = rs.getInt("id_tripulante");
                    int idSala = rs.getInt("id_sala");

                    //objetos necesarios para crear el constructor de tarea
                    Tripulante tripulante = tripulanteDAO.obtener(tripulanteAsignado);
                    Sala sala = salaDAO.obtener(idSala);

                    Tarea tarea = new Tarea(descripcion,tripulante,sala);
                    // se agregan los valores no considerados por el constructor
                    tarea.setCompletada(completada);
                    tarea.setId(idTarea);
                    return tarea;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener tarea: "+ e.getMessage());
        }
        return null;
    }

    // es como un get, se requiere statement y rs para leer solamente
    @Override
    public ArrayList<Tarea> obtenerTodos(){
        String sqlReadAll = "SELECT * FROM tarea";
        Connection con = DBUtil.getInstance().getConnection();

        ArrayList<Tarea> arrayTarea = new ArrayList<>();
        TripulanteDAO tripulanteDAO = new TripulanteDAOImpl();
        SalaDAO salaDAO = new SalaDAOImpl();

        try (
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sqlReadAll)
        ){
            while (rs.next()){
                //captura de columnas
                int idTarea = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                boolean completada = rs.getBoolean("completada");
                int tripulanteAsignado = rs.getInt("id_tripulante");
                int idSala = rs.getInt("id_sala");
                //objetos necesarios para crear el constructor de tarea
                Tripulante tripulante = tripulanteDAO.obtener(tripulanteAsignado);
                Sala sala = salaDAO.obtener(idSala);
                //se crea la tarea
                Tarea tarea = new Tarea(descripcion,tripulante,sala);
                // se agregan los valores no considerados por el constructor
                tarea.setCompletada(completada);
                tarea.setId(idTarea);
                //se agrega al array
                arrayTarea.add(tarea);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todas las tareas: "+e.getMessage());
        }

        return arrayTarea;
    }

    @Override
    public ArrayList<Tarea> obtenerPorTripulante(int idTrip){
        String sqlRead = "SELECT * FROM tarea WHERE id_tripulante=?";
        Connection con = DBUtil.getInstance().getConnection();

        ArrayList<Tarea> tareasTripulante = new ArrayList<>();
        TripulanteDAO tripulanteDAO = new TripulanteDAOImpl();
        SalaDAO salaDAO = new SalaDAOImpl();

        try (PreparedStatement pst = con.prepareStatement(sqlRead)) {
            pst.setInt(1,idTrip);

            try (ResultSet rs =pst.executeQuery()) {
                while (rs.next()) {
                    int idTarea = rs.getInt("id");
                    String descripcion = rs.getString("descripcion");
                    boolean completada = rs.getBoolean("completada");
                    int tripulanteAsignado = rs.getInt("id_tripulante");
                    int idSala = rs.getInt("id_sala");

                    //objetos necesarios para crear el constructor de tarea
                    Tripulante tripulante = tripulanteDAO.obtener(tripulanteAsignado);
                    Sala sala = salaDAO.obtener(idSala);

                    Tarea tarea = new Tarea(descripcion,tripulante,sala);
                    // se agregan los valores no considerados por el constructor
                    tarea.setCompletada(completada);
                    tarea.setId(idTarea);

                    tareasTripulante.add(tarea);

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tareasTripulante;
    }

    @Override
    public void actualizar(Tarea tarea){
        String sqlUpdate = "UPDATE tarea SET descripcion=?, completada=?, id_tripulante=?, id_sala=? WHERE id=?";
        Connection con = DBUtil.getInstance().getConnection();

        try (PreparedStatement pst = con.prepareStatement(sqlUpdate)) {

            pst.setString(1, tarea.getDescripcion());
            pst.setBoolean(2, tarea.isCompletada());
            pst.setInt(3, tarea.getTripulanteAsignado().getId());
            pst.setInt(4, tarea.getSala().getId());
            pst.setInt(5, tarea.getId());

            //para verificar
            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Tarea " + tarea.getId() + " actualizada con éxito.");
            } else {
                System.out.println("No se encontró la tarea con ID: " + tarea.getId());
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar tarea: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id){
        String sqlDelete = "DELETE FROM tarea WHERE id = ?";
        Connection con = DBUtil.getInstance().getConnection();

        try (PreparedStatement pst = con.prepareStatement(sqlDelete)) {
            pst.setInt(1, id);

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

}
