public class Capitan extends Tripulante {

    public Capitan(String nombre) {
        super(nombre, "capitan");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println(getNombre() + " puede convocar votaciones de emergencia.");
    }

    public void convocarVotacion(Nave nave) {
        nave.iniciarVotacion();
    }
}