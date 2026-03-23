public class Capitan extends Tripulante {

    public Capitan(String nombre,String rol) {
        super(nombre, rol);
    }

    @Override
    public void habilidadEspecial() {
        System.out.println(getNombre() + " puede convocar votaciones de emergencia.");
    }

    public void convocarVotacion(Nave nave) {
        nave.iniciarVotacion();
    }
}