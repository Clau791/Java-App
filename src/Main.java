import models.*;
import services.Service;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Service service = new Service();

        int modificariRamase = 10;
        boolean running = true;
        while (running) {
            System.out.println("\n===== Sistem de Rezervare a Sălilor =====");
            System.out.println("1. Creează cont");
            System.out.println("2. Vizualizează sălile disponibile");
            System.out.println("3. Vizualizează sălile disponibile dintr-o anumită facultate");
            System.out.println("4. Rezervă o sală de seminar");
            System.out.println("5. Rezervă o sală de laborator(doar pentru profesori)");
            System.out.println("6. Rezervă o sală de curs(doar pentru profesori)");
            System.out.println("7. Rezervă mai multe săli (maxim 3 pentru studenți)");
            System.out.println("8. Rezervă o sală recurent (doar pentru profesori)");
            System.out.println("9. Vezi rezervările tale");
            System.out.println("10. Sterge rezervare");
            System.out.println("11. Ieșire");
            System.out.print("Alege o opțiune: ");

            int opt = scanner.nextInt();
            scanner.nextLine();
            switch (opt) {
                case 1  -> service.creeazaCont();
                case 2  -> service.afiseazaSaliDisponibile();
                case 3  -> service.afiseazaSaliFacultate();
                case 4  -> service.rezervaSala(1);
                case 5  -> service.rezervaSala(2);
                case 6  -> service.rezervaSala(3);
                case 7  -> service.rezervareMultiple();
                case 8  -> service.rezervareRecurenta();
                case 9  -> service.veziRezervari();
                case 10 -> service.StergeRezervare();
                case 11 -> {
                    System.out.println("La revedere!");
                    running = false;
                }
                default -> System.out.println("Opțiune invalidă!");
            }
        }

    }
}


