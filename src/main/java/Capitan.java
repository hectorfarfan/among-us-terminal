public class Capitan extends Tripulante {
    public Capitan(String nombre, String rol) {
        super(nombre, "Capitan");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puede convocar votaciones.");
    }

    public void convocarVotacion(Nave nave){
        nave.iniciarVotacion();
    }

}
