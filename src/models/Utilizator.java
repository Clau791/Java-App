package models;

import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Pattern;

public class Utilizator {
    protected int id;
    private String nume;
    private String email;
    private int facultate;
    private final Vector<Rezervare> rezervari = new Vector<>();

    public int getId(){
        return id;
    };

    // validare email
    private boolean validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Constructor
    public Utilizator(String nume, String email, int facultate, int id) {
        this.id = id;
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

    public int getFacultate() {
        return facultate;
    }

    public void setFacultate(int facultate) {
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
        if (facultate != 0) {
            System.out.println("Facultate: " + facultate); // presupunem că Facultate are getNume()
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
