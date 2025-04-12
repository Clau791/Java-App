package services;

import models.*;

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;

public class Service {

    public Service(){
        initializareFacultati();

    }

    Scanner scanner = new Scanner(System.in);
    private final Vector<Facultate> facultati = new Vector<>();
    private final Vector<Utilizator> utilizatori = new Vector<>();

    // 1. Creează cont
    public void creeazaCont() {
        System.out.print("Student sau Profesor? ");
        String tip = scanner.nextLine();

        System.out.print("Nume: ");
        String nume = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.println("Alege Facultatea ta:");
        for (int i = 0; i < facultati.size(); i++) {
            System.out.println((i + 1) + ". " + facultati.get(i).getNume());
        }

        System.out.print("Introdu numărul facultății: ");
        int optiuneFacultate = scanner.nextInt();
        scanner.nextLine();

        Facultate facultate = facultati.get(optiuneFacultate - 1);  // Obținem facultatea selectată

        if (tip.equalsIgnoreCase("Student")) {
            System.out.print("An studiu: ");
            int anStudiu = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Grupa: ");
            int grupa = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Numar matricol: ");
            int nr_mtr = scanner.nextInt();
            scanner.nextLine();


            Student s = new Student(nume, email, facultate, anStudiu, grupa, nr_mtr);
            utilizatori.add(s);  // Adăugăm studentul la lista de utilizatori
            System.out.println("Cont student creat cu succes!");

        } else if (tip.equalsIgnoreCase("Profesor")) {
            System.out.print("Departament : ");
            String departament = scanner.nextLine();

            // cream contul
            Profesor p = new Profesor(nume, email, facultate, departament);
            utilizatori.add(p);
            System.out.println("Cont profesor creat cu succes!");


        } else {
            System.out.println("Tipul introdus nu este valid. Alege între 'Student' sau 'Profesor'.");
        }
    }


    // 2. Afișează săli disponibile
    public void afiseazaSaliDisponibile() {
        for (Facultate f : facultati) {
            for (Sala s : f.getSali()) {
                System.out.println("Sala: " + s.getNume() + " (Capacitate: " + s.getCapacitate() + ")");
            }
        }
    }

    // 3. Afișează săli dintr-o facultate
    public void afiseazaSaliFacultate() {

        System.out.println("Alege Facultatea :");
        for (int i = 0; i < facultati.size(); i++) {
            System.out.println((i + 1) + ". " + facultati.get(i).getNume());
        }

        System.out.print("Introdu numărul facultății: ");
        int optiuneFacultate = scanner.nextInt();
        scanner.nextLine();

        if (optiuneFacultate >= 1 && optiuneFacultate <= facultati.size()) {
            Facultate f = facultati.get(optiuneFacultate - 1);
            System.out.println("Săli disponibile în " + f.getNume() + ":");
            for (Sala s : f.getSali()) {
                System.out.println("Sala: " + s.getNume() + " (Capacitate: " + s.getCapacitate() + ")");
            }
        } else {
            System.out.println("Opțiune invalidă.");
        }
    }

    // 4. Rezervă o sală
    public void rezervaSala(int tip) {
        // tip 1 -> seminar, 2 -> laborator , 3-> curs
        if(tip != 1 && utilizatori.getFirst() instanceof Student){
            System.out.println("Studentii pot rezerva doar sali de seminar.");
            return;
        }

        System.out.print("Numele sălii: ");
        String numeSala = scanner.nextLine();

        System.out.print("Zi: ");
        int zi = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Interval orar (ex. 12:00-14:00): ");
        String interval = scanner.nextLine();

        // cautam daca sala exista
        Sala salaGasita = null;
        for (Facultate f : facultati) {
            for (Sala s : f.getSali()) {
                if (s.getNume().equalsIgnoreCase(numeSala)) {
                    salaGasita = s;
                    break;
                }
            }
            if (salaGasita != null) break;
        }

        if (salaGasita == null) {
            System.out.println("Sala nu există!");
            return;
        }

        Utilizator u = utilizatori.getFirst();

        // ca sa verificam daca s-a adaugat rezervarea comparam nr de rez ale salii dupa si inainte
        int before = salaGasita.getRez().size();
        Rezervare r = new Rezervare(salaGasita, u, zi, interval);
        int after = salaGasita.getRez().size();

        if (before != after) { // doar dacă a fost validata
            u.adaugaRezervare(r, salaGasita);
        }
    }

    // 7. Rezerva mai multe sali (max 3 pentru studenți)
    public void rezervareMultiple() {
        int count = 0;
        while (count < 3) {
            System.out.println("Rezervare #" + (count + 1));
            int tip = 2;
            if(utilizatori.getFirst() instanceof Student){
                tip = 1;
            }
            rezervaSala(tip);
            count++;

            System.out.print("Mai vrei să rezervi? (da/nu): ");
            String raspuns = scanner.nextLine();
            if (!raspuns.equalsIgnoreCase("da")) break;
        }
    }

    // 8. Rezervare recurentă (doar pentru profesori)
    public void rezervareRecurenta() {
        Utilizator u = utilizatori.getFirst();
        if (!(u instanceof Profesor)) {
            System.out.println("Doar profesorii pot face rezervări recurente.");
            return;
        }

        System.out.print("Numele sălii: ");
        String numeSala = scanner.nextLine();
        System.out.print("Interval orar (ex. 10:00-12:00): ");
        String interval = scanner.nextLine();

        // cautam daca sala exista
        Sala salaGasita = null;
        for (Facultate f : facultati) {
            for (Sala s : f.getSali()) {
                if (s.getNume().equalsIgnoreCase(numeSala)) {
                    salaGasita = s;
                    break;
                }
            }
            if (salaGasita != null) break;
        }

        if (salaGasita == null) {
            System.out.println("Sala nu există!");
            return;
        }


        for (int zi = 1; zi <= 5; zi++) {
            Rezervare r = new Rezervare(salaGasita, u, zi, interval);
            if (r.getId() >= 0) {
                u.adaugaRezervare(r, salaGasita);
            }
        }
    }

    // 9. Vezi rezervările
    public void veziRezervari() {
        Utilizator u =utilizatori.getFirst();
        u.afiseazaRezervari();

    }

    // 10. Corectează rezervări
    public void StergeRezervare() {
        Utilizator u = utilizatori.getFirst();
        Vector<Rezervare> rez = u.getRezervari();

        if (rez.isEmpty()) {
            System.out.println("Nu ai rezervări de șters.");
            return;
        }


        while (true) {
            System.out.println("Rezervările tale:");
            for (Rezervare r : rez) {
                r.afiseazaRezervare();
            }

            System.out.print("Alege numarul rezervarii pe care vrei să o ștergi: ");
            int index = scanner.nextInt() ; // utilizatorul ne da indexul incepand de la 1

            scanner.nextLine();

            if (index >= 1 && index < rez.size() + 1) {
                index = index - 1;

                Rezervare rezervareDeSters = rez.get(index);
                Sala sala = rezervareDeSters.getSala();

                // stergem rezervarea si din sala si din rezervari
                rez.remove(index);
                sala.getRez().remove(rezervareDeSters);

                System.out.println("Rezervarea a fost ștearsă cu succes!");
            } else {
                System.out.println("Index invalid!");
            }

            System.out.print("Mai dorești să ștergi o rezervare? (da/nu): ");
            String raspuns = scanner.nextLine();
            if (!raspuns.equalsIgnoreCase("da")) break;
        }
    }



    private void initializareFacultati() {
        // sali pentru FMI
        Sala sala1 = new Sala_Amfiteatru("Amfiteatru-1 FMI", 120);
        Sala sala2 = new Sala_Laborator("Laborator-1 FMI", 30);
        Sala sala3 = new Sala_Seminar("Seminar-1 FMI", 40);
        Vector<Sala> saliFMI = new Vector<>(Arrays.asList(sala1, sala2, sala3));
        Facultate fmi = new Facultate("FMI", saliFMI);

        // Sali pentru Arhitectura
        Sala sala4 = new Sala_Amfiteatru("Amfiteatru-1 Arh ", 150);
        Sala sala5 = new Sala_Laborator("Laborator-2 Arh ", 25);
        Vector<Sala> saliEnergetica = new Vector<>(Arrays.asList(sala4, sala5));
        Facultate Arhitectura = new Facultate("Arhitectura", saliEnergetica);

        facultati.add(fmi);
        facultati.add(Arhitectura);
    }
}
