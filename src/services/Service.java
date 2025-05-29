package services;

import models.*;

import javax.crypto.spec.PSource;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Service {

    // la apelarea constructorului initializam cateva sali si facultati
    public Service(){
        initializareDate();
    }
    RezervareService r = RezervareService.getInstance();
    Scanner scanner = new Scanner(System.in);
    private Vector<Facultate> facultati = new Vector<>();
    private final Vector<Utilizator> utilizatori = new Vector<>();
    private Vector<Sala> sali = new Vector<>();
    private Student student = null;
    private Profesor profesor = null;
    Vector<Integer> rez = new Vector<>();
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

        int id_facultate = facultati.get(optiuneFacultate - 1).getId() + 1;  // Obținem facultatea selectata

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

            r.creareContStudent(nume, email, anStudiu, nr_mtr, id_facultate);

        } else if (tip.equalsIgnoreCase("Profesor")) {
            System.out.print("Departament : ");
            String departament = scanner.nextLine();

            // cream contul
            r.creareContProfesor(nume, email, departament, id_facultate);

        } else {
            System.out.println("Tipul introdus nu este valid. Alege între 'Student' sau 'Profesor'.");
        }
        scanner.nextLine();

    }

    //2. Logheaza cont
    public void logheazaContStudent() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.println("Numarul matricol(student)/ID(profesor, primit la crearea contului): ");
        int nr_matricol = scanner.nextInt();
        scanner.nextLine();
        // cautam utilizatorul in baza de date
        Utilizator utilizatorGasit = null;
        Vector<Student> students = r.getStudents();
        Vector<Profesor> profesors = r.getProfesors();
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email) && s.getNr_matricol() == nr_matricol) {
                System.out.print("Bine ai venit, " + s.getNume() + "!");
                student = s;

                break;
            }
        }
        for (Profesor p :profesors){
            if(p.getEmail().equalsIgnoreCase(email) && p.getId() == nr_matricol){
                profesor = p;
                System.out.print("Bine ai venit, " + p.getNume() + "!");

                break;
            }
        }

        if (profesor == null && student == null) {
            System.out.println("Contul nu a fost gasit.");
        }
        scanner.nextLine();
    }

    // 3. Afișeaza sali disponibile
    public void afiseazaSaliDisponibile() {
        //facem loop in sali si le ordonam pe facultati
        for (Sala s : sali) {
            s.displaySala();
        }

        scanner.nextLine();
    }

//     4. Afișează sali dintr-o facultate
    public void afiseazaSaliFacultate() {

        System.out.println("Alege Facultatea :");
        for (int i = 0; i < facultati.size(); i++) {
            System.out.println((i + 1) + ". " + facultati.get(i).getNume());
        }

        System.out.print("Introdu numărul facultății: ");
        int optiuneFacultate = scanner.nextInt() ;
        scanner.nextLine();

        if (optiuneFacultate >= 1 && optiuneFacultate <= facultati.size() + 1) {

            Facultate f = facultati.get(optiuneFacultate - 1);
            System.out.println("Săli disponibile în " + f.getNume() + ":");
            r.PrintSaliFacultate(f);
        } else {
            System.out.println("Opțiune invalidă.");
        }
        scanner.nextLine();

    }
//
    // 5. Rezerva o sală
    public void rezervaSala(int tip) {
        Utilizator u ;
        if(student == null && profesor == null){
            System.out.println("Trebuie sa te loghezi.");
            scanner.nextLine();
            return;
        }
        if(student == null){
            u = profesor;
        }
        else u = student;

        // tip 1 -> seminar, 2 -> laborator , 3-> curs
        if(tip != 1 && student != null){
            System.out.println("Studentii pot rezerva doar sali de seminar.");
            scanner.nextLine();
            return;
        }

        System.out.print("Intrudu Numarul salii: ");
        int nrSala = scanner.nextInt();

        System.out.print("Zi: ");
        int zi = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Interval orar (ex. 12:00-14:00): ");
        String interval = scanner.nextLine();

        // cautam daca sala exista
        if(sali.size() < nrSala || nrSala< 0){
            System.out.println("Id Invalid");
        }
        Sala salaGasita = sali.get(nrSala - 1);

        if((salaGasita instanceof Sala_Amfiteatru || salaGasita instanceof Sala_Laborator) && student != null){
            System.out.println("Aceasta sala nu este una de seminar, alege o sala de seminar.");
            return;
        }
        // ca sa verificam daca s-a adaugat rezervarea comparam nr de rez ale salii dupa si inainte
        if(profesor != null){
            r.adaugaRezervareProfesor(zi, interval,nrSala, u.getId() + 1);
        }
        if (student != null){
            r.adaugaRezervareStudent(zi,interval, nrSala, u.getId() + 1);
            // se va afisa daca exista interval care se suprapune
            // doar daca a fost validata
            // daca rezervarea a fost facuta de un student scadem numarul de rez pe care le poate face

            // scadem 1 rezervare
            r.actualizareNrRezervariDisponibile(u.getId() );
        }
        scanner.nextLine();

    }



    // 6. Rezerva mai multe sali (max 3 pentru studenți)
    public void rezervareMultiple() {
        int tip;
        int count;
        // daca este student are doar dreptul la 3 rez pe luna
        // altfel este profesor si poate rezerva mai multe
        if (student != null){
            count = student.getNr_rez_disponibil();
            tip = 1;
        }
        else{
            tip = 2;
            count = 999999;
        }
        while (count > 0) {
            rezervaSala(tip);
            count--;
            if(student != null){
                r.actualizareNrRezervariDisponibile(student.getId());
            }

            System.out.print("Mai vrei să rezervi? (da/nu): ");
            String raspuns = scanner.nextLine();
            if (!raspuns.equalsIgnoreCase("da")) break;
        }
        // setam numarul de rezervari ramase

        scanner.nextLine();

    }
//
//    // 7. Rezervare recurenta (doar pentru profesori)
//    // pentru a rezerva o sala in fiecare zi a saptamanii
//    public void rezervareRecurenta() {
//        Utilizator u = utilizatori.getFirst();
//        if (!(u instanceof Profesor)) {
//            System.out.println("Doar profesorii pot face rezervări recurente.");
//            return;
//        }
//
//        System.out.print("Numele sălii: ");
//        String numeSala = scanner.nextLine();
//        System.out.print("Interval orar (ex. 10:00-12:00): ");
//        String interval = scanner.nextLine();
//
//        // cautam daca sala exista
//        Sala salaGasita = null;
//        for (Facultate f : facultati) {
//            for (Sala s : f.getSali()) {
//                if (s.getNume().equalsIgnoreCase(numeSala)) {
//                    salaGasita = s;
//                    break;
//                }
//            }
//            if (salaGasita != null) break;
//        }
//
//        if (salaGasita == null) {
//            System.out.println("Sala nu există!");
//            return;
//        }
//
//
//        for (int zi = 1; zi <= 5; zi++) {
//
//            int before = salaGasita.getRez().size();
//            Rezervare r = new Rezervare(salaGasita, u, zi, interval);
//            int after = salaGasita.getRez().size();
//
//            if (before != after) {
//                u.adaugaRezervare(r, salaGasita);
//            }
//        }
//        scanner.nextLine();
//
//    }

    // 8. Vezi rezervarile
    public void veziRezervari() {

        if(profesor != null){
            rez =  r.afiseazaRezervariPersoana("profesor", profesor.getId());
            if(rez.isEmpty()){
                System.out.println("Nu ai nicio rezervare !");
            }
            scanner.nextLine();

        }
        if(student != null){

            rez =  r.afiseazaRezervariPersoana("student", student.getId());
            if(rez.isEmpty()){
                System.out.println("Nu ai nicio rezervare !");
            }
            scanner.nextLine();

        }
    }

    // 9. Sterge rezervare
    public void StergeRezervare() {
        if(student == null && profesor == null){
            System.out.println("Trebuie sa te loghezi.");
            scanner.nextLine();
            return;
        }
        if (rez.size() == 0) {
            System.out.println("Nu ai nicio rezervare !");
            scanner.nextLine();
            return;
        }

        while (true) {

            System.out.println("Rezervarile tale:");
            veziRezervari();

            System.out.print("Alege numarul rezervarii pe care vrei sa o ștergi: ");
            int index = scanner.nextInt() ; // utilizatorul ne da indexul incepand de la 1

            scanner.nextLine();

            if (index >= 1 && index <= rez.size() ) {
                index = index - 1;
                r.stergeRezervare(rez.get(index));
                break;
            } else {
                System.out.println("Index invalid!");
            }

        }


    }


    private boolean validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private void initializareDate() {
        // sali pentru FMI
        facultati = r.getFacultati();
        sali = r.getSali();

    }
}
