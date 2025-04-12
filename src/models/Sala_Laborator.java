package models;

public class Sala_Laborator extends Sala {

    public Sala_Laborator(String nume, int capacitate) {
        super(nume, capacitate);
    }

    @Override
    public void displaySala() {
        System.out.println("Laborator: " + getNume() + ", Capacitate: " + getCapacitate());
    }
}
