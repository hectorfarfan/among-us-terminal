import java.util.ArrayList;

public interface TripulanteDAO {
    void insertar(Tripulante tripulante);
    Tripulante obtener(int id);
    ArrayList<Tripulante> obtenerTodos();
    void actualizar(Tripulante tripulante);
    void eliminar(int id);
}