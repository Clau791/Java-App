import models.*;
import services.Service;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Service service = new Service();

        System.out.println("\\n=== Sistem de rezervare a sălilor ===");

        int modificariRamase = 10;
        boolean running = true;
        while (running) {
            System.out.println("\n===== Sistem de Rezervare a Sălilor =====");
            System.out.println("1. Creează cont");
            System.out.println("2. Vizualizează sălile disponibile");
            System.out.println("3. Vizualizează sălile disponibile dintr-o anumită facultate");
            System.out.println("4. Rezervă o sală");
            System.out.println("5. Rezervă mai multe săli (maxim 3 pentru studenți)");
            System.out.println("6. Rezervă o sală recurent (doar pentru profesori)");
            System.out.println("7. Vezi rezervările tale");
            System.out.println("8. Corectează rezervări (maxim 10 modificări)");
            System.out.println("9. Ieșire");
            System.out.print("Alege o opțiune: ");

            int opt = scanner.nextInt();
            scanner.nextLine();
            switch (opt) {
                case 1 -> service.creeazaCont();
                case 2 -> service.afiseazaSaliDisponibile();
                case 3 -> service.afiseazaSaliFacultate();
                case 4 -> service.rezervaSala();
                case 5 -> service.rezervareMultiple();
                case 6 -> service.rezervareRecurenta();
                case 7 -> service.veziRezervari();
                case 8 -> service.StergeRezervare();
                case 9 -> {
                    System.out.println("La revedere!");
                    running = false;
                }
                default -> System.out.println("Opțiune invalidă!");
            }
        }

    }
}


// 
// 📌 Obiecte principale

// Sala (id, nume, capacitate, rezervari)
// Amfiteatru (id, nume, capacitate, disponibilitate) - derivat din Sala
// Rezervare (id, sala, utilizator, data, interval_orar)
// Utilizator (id, nume, tip utilizator - student/profesor/admin)
// Eveniment (id, titlu, sală, organizator, descriere)
// Facultate(id, nume, sali )

// 📌 Acțiuni posibile
// ✔️ Căutare săli disponibile
// ✔️ Creare rezervare
// ✔️ Anulare rezervare
// ✔️ Vizualizare istoric rezervări
// ✔️ Gestionare drepturi utilizatori (ex: admin poate bloca săli)