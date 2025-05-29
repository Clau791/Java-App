package models;

public final class Profesor extends Utilizator{

    private String departament;

    public Profesor(String nume, String email, int facultate, String departament, int id) {
        super(nume, email, facultate,id);
        this.departament = departament;

    }


    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public void solicitaRezervare(Sala sala, int zi, String interval) {
        Rezervare r = new Rezervare(sala, this, zi, interval);
        this.adaugaRezervare(r, sala);
        System.out.println("Profesorul " + getNume() + " a rezervat sala " + sala.getNume());
    }

    @Override
    public void afisareProfil() {
        super.afisareProfil();
        System.out.println("Departament: " + departament);
    }


}
