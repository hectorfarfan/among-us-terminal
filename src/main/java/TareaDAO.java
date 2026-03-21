import java.util.ArrayList;

public interface TareaDAO {
    void insertar(Tarea tarea);
    Tarea obtener(int id);
    ArrayList<Tarea> obtenerTodos();
    ArrayList<Tarea> obtenerPorTripulante(int idTrip);
    void actualizar(Tarea tarea);
    void eliminar(int id);

}
