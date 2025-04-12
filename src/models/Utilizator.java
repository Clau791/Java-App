package models;

import java.util.Vector;

public class Utilizator {
    private String nume;
    private String email;
    private Facultate facultate;
    private final Vector<Rezervare> rezervari = new Vector<>();

    // Constructor
    public Utilizator(String nume, String email, Facultate facultate) {
        this.nume = nume;
        this.email = email;
        this.facultate = facultate;
    }

    // Getters si Setters
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Facultate getFacultate() {
        return facultate;
    }

    public void setFacultate(Facultate facultate) {
        this.facultate = facultate;
    }

    public Vector<Rezervare> getRezervari() {
        return rezervari;
    }


    public void adaugaRezervare(Rezervare r, Sala s) {
        rezervari.add(r);
        s.addRez(r);
    }

    // Informatii Utilizator
    public void afisareProfil() {
        System.out.println("Nume: " + nume);
        System.out.println("Email: " + email);
        if (facultate != null) {
            System.out.println("Facultate: " + facultate.getNume()); // presupunem că Facultate are getNume()
        }
    }

    // Afișeaza rez utilizatorului
    public void afiseazaRezervari() {
        if (rezervari.isEmpty()) {
            System.out.println("Nu există rezervări.");
        } else {
            for (Rezervare r : rezervari) {

                System.out.println("Rezervare în sala " + r.getSala().getNume() +
                        " pe data de " + r.getZi() +
                        " între " + r.getOra() );
            }
        }
    }
}
