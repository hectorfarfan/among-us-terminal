import java.util.ArrayList;

public interface SalaDAO {
    void insertar(Sala sala);
    Sala obtener(int id);
    ArrayList<Sala> obtenerTodos();
    void actualizar(Sala sala);
    void eliminar(int id);
}