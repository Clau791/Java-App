package models;

import java.util.Vector;

public class Sala {
    // Sala (id, nume, capacitate)
    private static int sali = 0;
    private final int id;
    private String nume;
    private final int capacitate;
    private final Vector<Rezervare> rezervari = new Vector<>();

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
    
    public void addRez(Rezervare R){
        rezervari.add(R);
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
