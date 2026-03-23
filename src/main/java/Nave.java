import java.util.ArrayList;
import java.util.Scanner;

public class Nave {
    private ArrayList<Tripulante> tripulantes;
    private ArrayList<Sala> salas;
    private ArrayList<Tarea> tareas;
    private Scanner sc;

    private TareaDAO taDao = new TareaDAOImpl();
    private TripulanteDAO tDao = new TripulanteDAOImpl();
    private SalaDAO sDao = new SalaDAOImpl();

    public Nave(ArrayList<Tripulante> tripulantes, ArrayList<Sala> salas, ArrayList<Tarea> tareas) {
        this.tripulantes = tripulantes;
        this.salas = salas;
        this.tareas = tareas;
        this.sc = new Scanner(System.in);
    }

    public void turno(Tripulante t) {
        limpiarPantalla();
        System.out.println("¡Pasa el ordenador a " + t.getNombre() + "!");
        System.out.println("Pulsa Enter cuando estes listo...");
        sc.nextLine();

        System.out.println("\n>>> TURNO DE " + t.getNombre().toUpperCase() + " <<<");
        System.out.println("Tu rol secreto: " + t.getRol().toUpperCase());

        mostrarEstadoNave();

        if (t instanceof Impostor) {
            menuImpostor((Impostor) t);
        } else {
            menuTripulante(t);
        }
    }

    private void menuTripulante(Tripulante t) {
        System.out.println("\n¿Que quieres hacer?");
        System.out.println("1) Realizar tarea");
        System.out.println("2) Usar habilidad especial");
        System.out.println("3) Convocar votacion de emergencia");
        System.out.println("4) Pasar turno");

        int op = Integer.parseInt(sc.nextLine());

        if (op == 1){
            System.out.println("Tus tareas pendientes:");
            for (int i = 0; i < tareas.size(); i++) {
                Tarea tar = tareas.get(i);
                if (!tar.isCompletada() && tar.getTripulanteAsignado().getId() == t.getId()) {
                    System.out.println("ID: " + tar.getId() + " - " + tar.getDescripcion());
                }
            }
            System.out.print("Elige tarea por ID: ");
            int idTarea = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < tareas.size(); i++) {
                Tarea seleccionada = tareas.get(i);
                if (seleccionada.getId() == idTarea) {
                    t.realizarTarea(seleccionada);
                    taDao.actualizar(seleccionada);
                    break;
                }
            }
        } else if(op == 2) {
            t.habilidadEspecial();
            if (t instanceof Ingeniero) {
                System.out.println("Elige ID de sala a reparar:");
                for (Sala s : salas) {
                    if (s.isSaboteada()) System.out.println(s.getId() + " - " + s.getNombre());
                }
                int idSala = Integer.parseInt(sc.nextLine());
                for (Sala s : salas) {
                    if (s.getId() == idSala) {
                        ((Ingeniero) t).repararSala(s);
                        sDao.actualizar(s);
                        break;
                    }
                }
            }
        } else if (op == 3) {
            iniciarVotacion();
        }
    }

    private void menuImpostor(Impostor i) {
        System.out.println("\n¿Que quieres hacer?");
        System.out.println("1) Simular tarea (no se completa)");
        System.out.println("2) Sabotear una sala");
        System.out.println("3) Eliminar a un tripulante");
        System.out.println("4) Convocar votacion (para disimular)");
        System.out.println("5) Pasar turno");

        int op = Integer.parseInt(sc.nextLine());

        if (op == 2) {
            System.out.println("Salas disponibles:");
            for (Sala s : salas) System.out.println(s.getId() + " - " + s.getNombre());
            System.out.print("Elige ID de sala a sabotear: ");
            int idSala = Integer.parseInt(sc.nextLine());

            for (Sala s : salas) {
                if (s.getId() == idSala) {
                    i.sabotear(s);
                    sDao.actualizar(s);
                    break;
                }
            }
        } else if (op == 3) {
            System.out.println("Tripulantes vivos:");
            for (Tripulante trip : tripulantes) {
                if (trip.isVivo() && trip.getId() != i.getId()) {
                    System.out.println(trip.getId() + " - " + trip.getNombre());
                }
            }
            System.out.print("Elige ID de victima: ");
            int idVictima = Integer.parseInt(sc.nextLine());

            for (Tripulante victima : tripulantes) {
                if (victima.getId() == idVictima) {
                    i.eliminar(victima);
                    tDao.actualizar(victima);
                    break;
                }
            }
        } else if (op == 4) {
            iniciarVotacion();
        }
    }

    public void iniciarVotacion() {
        System.out.println("\n!!! REUNION DE EMERGENCIA !!!");
        int[] conteoVotos = new int[tripulantes.size()];

        for (Tripulante votante : tripulantes) {
            if (votante.isVivo()) {
                limpiarPantalla();
                System.out.println("Turno de voto de: " + votante.getNombre());
                System.out.println("¿A quien votas? Elige el numero de la lista (0 para saltar):");

                for (int i = 0; i < tripulantes.size(); i++) {
                    if (tripulantes.get(i).isVivo()) {
                        System.out.println((i + 1) + ") " + tripulantes.get(i).getNombre());
                    }
                }

                int voto = Integer.parseInt(sc.nextLine());
                if (voto > 0 && voto <= tripulantes.size()) {
                    conteoVotos[voto - 1]++;
                }
            }
        }

        int maxVotos = 0;
        int indexExpulsado = -1;

        for (int i = 0; i < conteoVotos.length; i++) {
            if (conteoVotos[i] > maxVotos) {
                maxVotos = conteoVotos[i];
                indexExpulsado = i;
            }
        }

        if (indexExpulsado != -1) {
            Tripulante expulsado = tripulantes.get(indexExpulsado);
            System.out.println("El jugador " + expulsado.getNombre() + " ha sido expulsado.");
            expulsado.setVivo(false);
            tDao.actualizar(expulsado);
        } else {
            System.out.println("Nadie ha sido expulsado (empate o todos saltaron).");
        }
    }

    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void mostrarEstadoNave() {
        System.out.println("\n=== ESTADO DE LA NAVE ===");
        int tareasCompletadas = 0;
        for (Tarea t : tareas) {
            if (t.isCompletada()) tareasCompletadas++;
        }
        System.out.println("Tareas completadas: [" + tareasCompletadas + "/" + tareas.size() + "]");

        boolean haySabotaje = false;
        for (Sala s : salas) {
            if (s.isSaboteada()) haySabotaje = true;
        }
        System.out.println("SALA SABOTEADA: " + (haySabotaje ? "SI" : "NO"));
    }

    public boolean verificarVictoriaTripulantes() {
        boolean todasTareasCompletas = true;
        for (Tarea t : tareas) {
            if (!t.isCompletada()) {
                todasTareasCompletas = false;
                break;
            }
        }

        boolean impostorMuerto = true;
        for (Tripulante t : tripulantes) {
            if (t instanceof Impostor && t.isVivo()) {
                impostorMuerto = false;
                break;
            }
        }
        return todasTareasCompletas || impostorMuerto;
    }

    public boolean verificarVictoriaImpostor() {
        int malosVivos = 0;
        int buenosVivos = 0;

        for (Tripulante t : tripulantes) {
            if (t.isVivo()) {
                if (t instanceof Impostor) {
                    malosVivos++;
                } else {
                    buenosVivos++;
                }
            }
        }
        return malosVivos >= buenosVivos;
    }

    public ArrayList<Tripulante> getTripulantes() {
        return tripulantes;
    }
}
