public class Impostor extends Tripulante implements Saboteable {

    public Impostor(String nombre) {
        super(nombre, "impostor");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println(getNombre() + " puede sabotear salas y eliminar tripulantes.");
    }

    @Override
    public void sabotear(Sala sala) {
        sala.setSaboteada(true);
        System.out.println(getNombre() + " ha saboteado " + sala.getNombre());
    }

    public void eliminar(Tripulante tripulante) {
        if (tripulante.getId() == this.getId()) {
            System.out.println("No puedes eliminarte a ti mismo.");
            return;
        }
        tripulante.setVivo(false);
        System.out.println(getNombre() + " ha eliminado a " + tripulante.getNombre() + " en secreto.");
    }

    @Override
    public void realizarTarea(Tarea tarea) {
        System.out.println(getNombre() + " simula realizar la tarea... pero no la completa.");
    }
}