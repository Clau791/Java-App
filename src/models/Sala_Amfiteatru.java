package models;

public class Sala_Amfiteatru extends Sala {

    public Sala_Amfiteatru(String nume, int capacitate) {
        super(nume, capacitate);
    }

    @Override
    public void displaySala() {
        System.out.println("Amfiteatru: " + getNume() + ", Capacitate: " + getCapacitate());
    }
}
