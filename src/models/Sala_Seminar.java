package models;

public class Sala_Seminar extends Sala {

    public Sala_Seminar(String nume, int capacitate) {
        super(nume, capacitate);
    }

    @Override
    public void displaySala() {
        System.out.println("Seminar: " + getNume() + ", Capacitate: " + getCapacitate());
    }
}
