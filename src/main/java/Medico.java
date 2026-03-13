public class Medico extends Tripulante{
    public Medico(String nombre, String rol) {
        super(nombre, "Medico");
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Puede examinar tripulantes.");
    }

    public void examinar(Tripulante tripulante){
        System.out.println(tripulante.toString());
    }

}
