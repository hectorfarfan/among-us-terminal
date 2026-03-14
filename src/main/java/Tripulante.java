public abstract class Tripulante implements Votable,Trabajable {
    private int id;
    private String nombre;
    private String rol;
    private  boolean vivo;

    public Tripulante(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.vivo=true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    @Override
    public void realizarTarea(Tarea tarea) {
        if (this.vivo && tarea.getTripulanteAsignado().getId() == this.id) {
            tarea.setCompletada(true);
            System.out.println(nombre + " ha completado: " + tarea.getDescripcion());
        } else {
            System.out.println("No puedes realizar esta tarea.");
        }
    }

    @Override
    public void votar(Tripulante sospechoso) {
        System.out.println(nombre + " vota contra " + sospechoso.getNombre());
    }

    public abstract void habilidadEspecial();

    @Override
    public String toString() {
        return "Tripulante{" + "id=" + id + ", nombre='" + nombre + '\'' + ", rol='" + rol + '\'' + ", estado=" + vivo + '}';
    }
}
