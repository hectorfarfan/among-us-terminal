import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TripulanteDAO tDao = new TripulanteDAOImpl();
        SalaDAO sDao = new SalaDAOImpl();
        TareaDAO taDao = new TareaDAOImpl();

        System.out.println("=== AMONG US TERMINAL NAVE ESPACIAL ===");

        int numJugadores = 0;
        while (numJugadores < 4 || numJugadores > 10) {
            System.out.print("¿Cuántos jugadores van a jugar? (4-10): ");
            numJugadores = Integer.parseInt(sc.nextLine());
        }

        ArrayList<String> rolesDisponibles = new ArrayList<>();
        rolesDisponibles.add("impostor");
        rolesDisponibles.add("capitan");

        for (int i = 2; i < numJugadores; i++) {
            if (i % 2 == 0) {
                rolesDisponibles.add("ingeniero");
            } else {
                rolesDisponibles.add("medico");
            }
        }
        Collections.shuffle(rolesDisponibles);

        ArrayList<Tripulante> listaTripulantes = new ArrayList<>();
        for (int i = 0; i < numJugadores; i++) {
            System.out.print("Nombre del tripulante " + (i + 1) + ": ");
            String nombre = sc.nextLine();
            String rolAsignado = rolesDisponibles.get(i);

            Tripulante nuevoTripulante = null;
            switch (rolAsignado) {
                case "impostor":
                    nuevoTripulante = new Impostor(nombre, rolAsignado);
                    break;
                case "capitan":
                    nuevoTripulante = new Capitan(nombre, rolAsignado);
                    break;
                case "medico":
                    nuevoTripulante = new Medico(nombre, rolAsignado);
                    break;
                case "ingeniero":
                    nuevoTripulante = new Ingeniero(nombre, rolAsignado);
                    break;
            }

            tDao.insertar(nuevoTripulante); // Guardar en db
            listaTripulantes.add(nuevoTripulante);
        }

        System.out.println("Creando entorno...");
        ArrayList<Sala> listaSalas = new ArrayList<>();
        listaSalas.add(new Sala("Reactor"));
        listaSalas.add(new Sala("Cafeteria"));
        listaSalas.add(new Sala("Navegacion"));
        listaSalas.add(new Sala("Electricidad"));
        listaSalas.add(new Sala("Armamento"));
        listaSalas.add(new Sala("Comunicaciones"));

        for (Sala s : listaSalas) {
            sDao.insertar(s);
        }

        ArrayList<Tarea> listaTareas = new ArrayList<>();
        int idTmp = 1;
        for (Tripulante t : listaTripulantes) {
            if (!(t instanceof Impostor)) {

                Tarea t1 = new Tarea("Calibrar  equipos", t, listaSalas.get(0));
                Tarea t2 = new Tarea("Limpiar filtros", t, listaSalas.get(1));

                taDao.insertar(t1);
                taDao.insertar(t2);

                if (t1.getId() == 0) t1.setId(idTmp++);
                if (t2.getId() == 0) t2.setId(idTmp++);

                listaTareas.add(t1);
                listaTareas.add(t2);
            }
        }

        System.out.println("¡Todo listo! Que empiece la partida.");

        Nave nave = new Nave(listaTripulantes, listaSalas, listaTareas);
        boolean finPartida = false;

        while (!finPartida) {
            for (Tripulante t : nave.getTripulantes()) {
                if (t.isVivo()) {
                    nave.turno(t);
                    if (nave.verificarVictoriaTripulantes()) {
                        System.out.println("\n¡VICTORIA DE LOS TRIPULANTES!");
                        finPartida = true;
                        break;
                    } else if (nave.verificarVictoriaImpostor()) {
                        System.out.println("\n¡VICTORIA DEL IMPOSTOR!");
                        finPartida = true;
                        break;
                    }
                }
            }
        }
        System.out.println("Partida finalizada. gracias por jugar.");
    }
}