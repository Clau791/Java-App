package services;

import models.*;

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Service {

    // la apelarea constructorului initializam cateva sali si facultati
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

        // validare email
        if (!validEmail(email)) {
            System.out.println("Adresa de email invalidă!");
            creeazaCont();
        }
        System.out.println("Alege Facultatea ta:");
        for (int i = 0; i < facultati.size(); i++) {
            System.out.println((i + 1) + ". " + facultati.get(i).getNume());
        }

        System.out.print("Introdu numărul facultății: ");
        int optiuneFacultate = scanner.nextInt();
        scanner.nextLine();

        Facultate facultate = facultati.get(optiuneFacultate - 1);  // Obținem facultatea selectata

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
        scanner.nextLine();

    }


    // 2. Afișeaza sali disponibile
    public void afiseazaSaliDisponibile() {
        for (Facultate f : facultati) {
            for (Sala s : f.getSali()) {
                s.displaySala();
            }
        }
        scanner.nextLine();

    }

    // 3. Afișează sali dintr-o facultate
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
                s.displaySala();
            }
        } else {
            System.out.println("Opțiune invalidă.");
        }
        scanner.nextLine();

    }

    // 4. Rezerva o sală
    public void rezervaSala(int tip) {
        Utilizator u = utilizatori.getFirst();

        // tip 1 -> seminar, 2 -> laborator , 3-> curs
        if(tip != 1 && utilizatori.getFirst() instanceof Student){
            System.out.println("Studentii pot rezerva doar sali de seminar.");
            return;
        }
        if (u instanceof Student){
            if ( ((Student) u).getNr_rez_disponibil() == 0){
                System.out.println("Nu mai poti face rezervari !");
            }
        }

        System.out.print("Numele salii: ");
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
            System.out.println("Sala nu exista!");
            return;
        }

        // ca sa verificam daca s-a adaugat rezervarea comparam nr de rez ale salii dupa si inainte
        int before = salaGasita.getRez().size();
        Rezervare r = new Rezervare(salaGasita, u, zi, interval);
        int after = salaGasita.getRez().size();

        // se va afisa daca exista interval care se suprapune
        if (before != after) { // doar daca a fost validata
            u.adaugaRezervare(r, salaGasita);
            // daca rezervarea a fost facuta de un student scadem numarul de rez pe care le poate face
            if (u instanceof Student){
                // scadem 1 rezervare
                ((Student) u).setNr_rez_disponibil(1);
                System.out.println("Mai ai la dispozitie " + ((Student) u ).getNr_rez_disponibil()+ " rezervare/rezervari");
            }
        }
        scanner.nextLine();

    }

    // 7. Rezerva mai multe sali (max 3 pentru studenți)
    public void rezervareMultiple() {
        Utilizator u = utilizatori.getFirst();
        int count;
        // daca este student are doar dreptul la 3 rez pe luna
        // altfel este profesor si poate rezerva mai multe
        if (u instanceof Student){
            count = ((Student) u).getNr_rez_disponibil();
        }
        else{
            count = 999999;
        }
        while (count < 3) {
            System.out.println("Rezervare #" + (count + 1));
            int tip = 2;

            if(utilizatori.getFirst() instanceof Student){
                tip = 1;
            }
            rezervaSala(tip);
            count--;

            System.out.print("Mai vrei să rezervi? (da/nu): ");
            String raspuns = scanner.nextLine();
            if (!raspuns.equalsIgnoreCase("da")) break;
        }
        // setam numarul de rezervari ramase
        if (u instanceof Student){
            ((Student) u).setNr_rez_disponibil(count);
        }
        scanner.nextLine();

    }

    // 8. Rezervare recurenta (doar pentru profesori)
    // pentru a rezerva o sala in fiecare zi a saptamanii
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

            int before = salaGasita.getRez().size();
            Rezervare r = new Rezervare(salaGasita, u, zi, interval);
            int after = salaGasita.getRez().size();

            if (before != after) {
                u.adaugaRezervare(r, salaGasita);
            }
        }
        scanner.nextLine();

    }

    // 9. Vezi rezervarile
    public void veziRezervari() {
        Utilizator u =utilizatori.getFirst();
        u.afiseazaRezervari();
        scanner.nextLine();

    }

    // 10. Corecteaza rezervari
    public void StergeRezervare() {
        Utilizator u = utilizatori.getFirst();
        Vector<Rezervare> rez = u.getRezervari();

        if (rez.isEmpty()) {
            System.out.println("Nu ai rezervari de sters.");
            return;
        }

        while (true) {
            System.out.println("Rezervarile tale:");
            for (Rezervare r : rez) {
                r.afiseazaRezervare();
            }

            System.out.print("Alege numarul rezervarii pe care vrei sa o ștergi: ");
            int index = scanner.nextInt() ; // utilizatorul ne da indexul incepand de la 1

            scanner.nextLine();

            if (index >= 1 && index < rez.size() + 1) {
                index = index - 1;

                Rezervare rezervareDeSters = rez.get(index);
                Sala sala = rezervareDeSters.getSala();

                // stergem rezervarea si din sala si din rezervari
                rez.remove(index);
                sala.getRez().remove(rezervareDeSters);

                System.out.println("Rezervarea a fost ștearsa cu succes!");
            } else {
                System.out.println("Index invalid!");
            }

            System.out.print("Mai dorești sa ștergi o rezervare? (da/nu): ");
            String raspuns = scanner.nextLine();
            if (!raspuns.equalsIgnoreCase("da")) break;
        }
        scanner.nextLine();

    }


    private boolean validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
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
