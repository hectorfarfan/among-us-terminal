public class Ingeniero extends Tripulante {
    public Ingeniero(String nombre, String rol) {
        super(nombre, "Ingeniero");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puede reparar salas.");
    }

    public void repararSala(Sala sala){
        if (sala.isSaboteada()){
            sala.setSaboteada(false);
            System.out.println(getNombre() + "ha reparado "+sala.getNombre());
        }else {
            System.out.println("La sala "+sala.getNombre()+" no está saboteada.");
        }
    }
}
