package models;

import java.util.Vector;
public class Facultate {
    private final int id ;
    private static int contor = 0;
    private final String nume ;
    private Vector<Sala> sali;

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public Vector<Sala> getSali(){
        return sali;
    }

    public void addSala(Sala sala){
        sali.add(sala);
    }

    public Facultate(String nume, Vector<Sala> sali){
        this.id = contor++;
        this.nume = nume;
        this.sali = sali;
    }
}
