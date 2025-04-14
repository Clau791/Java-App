package models;

import java.util.HashMap;
import java.util.Vector;

public class Sala {
    // Sala (id, nume, capacitate)
    private static int sali = 0;
    private final int id;
    private String nume;
    private final int capacitate;

    private final Vector<Rezervare> rezervari = new Vector<>(); // colectie de date pe care o sortam

    public int getId() {
        return id;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public String getNume(){
        return nume;
    }

    public Vector<Rezervare> getRez(){
        return rezervari;
    }

    public void setNume(String name) {
        this.nume = name;
    }

    // Transforma ora de tipul "12:00-14:00" în minute (doar ora de început)

    private int parseStart(String ora) {
        String[] parts = ora.split("[-:]");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    public void addRez(Rezervare r) {
        int zi = r.getZi();
        int startR = parseStart(r.getOra());

        int i = 0;
        while (i < rezervari.size()) {
            Rezervare stocata = rezervari.get(i);
            if (zi < stocata.getZi()) break;
            if (zi == stocata.getZi() && startR < parseStart(stocata.getOra())) break;
            i++;
        }
        rezervari.add(i, r); // inserează sortat
    }


    public Sala(String nume, int capacitate) {
        this.id = sali++;
        this.nume = nume;
        this.capacitate = capacitate;

    }

    public void displaySala(){

        System.out.println("ID: " + getId());
        System.out.println("Nume: " + getNume());
        System.out.println("Capacitate: " + getCapacitate());
        System.out.println("Rezervarile salii: ");

        for(Rezervare rez : getRez()){
            // obiecte de tip rezervare care au ca data hashmap cu toate programarile din zi ale salii
            System.out.println("Data: " + rez.getData());
            break;
        }

    }

}
