import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersonaDAO personaDAO = new PersonaDAO();
        VisitaDAO visitaDAO = new VisitaDAO();

        boolean running = true;

        while (running) {
            System.out.println("\n=============================");
            System.out.println("     CLINICA MEDICA MENU     ");
            System.out.println("=============================");
            System.out.println("1. Aggiungi paziente");
            System.out.println("2. Aggiungi visita");
            System.out.println("3. Processa prossima visita");
            System.out.println("4. Rimuovi visita");
            System.out.println("5. Mostra pazienti");
            System.out.println("6. Mostra visite attive");
            System.out.println("7. Mostra visite processate");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            int scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1:
                    System.out.print("Nome paziente: ");
                    String nome = scanner.nextLine();
                    personaDAO.inserisci(new Persona(nome));
                    System.out.println("Paziente aggiunto.");
                    break;

                case 2:
                    System.out.print("Id paziente: ");
                    String idPaz = scanner.nextLine();
                    Persona paz = personaDAO.trovaById(idPaz);

                    if (paz == null) {
                        System.out.println("Paziente non trovato!");
                        break;
                    }

                    System.out.print("Data e ora (YYYY-MM-DD HH:MM:SS): ");
                    String dataOra = scanner.nextLine();
                    System.out.print("Tipo visita: ");
                    String tipo = scanner.nextLine();
                    System.out.print("Priorita (true/false): ");
                    boolean priorita = Boolean.parseBoolean(scanner.nextLine());

                    visitaDAO.inserisci(new Visita(dataOra, tipo, priorita, paz));
                    System.out.println("Visita aggiunta.");
                    break;

                case 3:
                    visitaDAO.processaProssimaVisita();
                    break;

                case 4:
                    System.out.print("Data e ora visita da eliminare: ");
                    String dataElimina = scanner.nextLine();
                    System.out.print("Id paziente: ");
                    int idElimina = Integer.parseInt(scanner.nextLine());

                    visitaDAO.elimina(dataElimina, idElimina);
                    System.out.println("Visita eliminata.");
                    break;

                case 5:
                    for (Persona persona : personaDAO.trovaTutti()) {
                        System.out.println(persona);
                    }
                    break;

                case 6:
                    for (Visita visita : visitaDAO.trovaTutti()) {
                        System.out.println(visita);
                    }
                    break;

                case 7:
                    for (Visita visita : visitaDAO.trovaRecord()) {
                        System.out.println(visita);
                    }
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Scelta non valida.");
            }
        }

        scanner.close();
    }
}