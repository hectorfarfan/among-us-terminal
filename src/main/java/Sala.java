public class Sala {
    private int id;
    private String nombre;
    private boolean saboteada;

    public Sala(String nombre) {
        this.nombre = nombre;
        this.saboteada = false;
    }

    public Sala(int id, String nombre, boolean saboteada) {
        this.id = id;
        this.nombre = nombre;
        this.saboteada = saboteada;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public boolean isSaboteada() { return saboteada; }
    public void setSaboteada(boolean saboteada) { this.saboteada = saboteada; }

    @Override
    public String toString() {
        return "Sala{id=" + id + ", nombre='" + nombre + "', saboteada=" + saboteada + '}';
    }
}