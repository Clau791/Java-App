package models;

public final class Student extends Utilizator{
    private int An_Studiu;
    private final int grupa;
    private final int Nr_Matricol;
    private int nr_rez_disponibil = 3;

    public Student(String nume, String email, Facultate facultate, int AnStudiu, int grupa, int nr_matricol) {
        super(nume, email, facultate);
        this.An_Studiu = AnStudiu;
        this.grupa = grupa;
        this.Nr_Matricol = nr_matricol;
    }


    public int getAnStudiu() {
        return An_Studiu;
    }

    public void setAnStudiu(int anStudiu) {
        this.An_Studiu = anStudiu;
    }

    public int getNr_rez_disponibil() {
        return nr_rez_disponibil;
    }

    public void setNr_rez_disponibil(int count) {
        this.nr_rez_disponibil = count;
    }

    public void solicitaRezervare(Sala sala, int zi, String interval) {
        Rezervare rezervare = new Rezervare(sala, this, zi, interval);
        adaugaRezervare(rezervare, sala);
        System.out.println("Rezervare solicitata pentru sala " + sala.getNume());
        nr_rez_disponibil = nr_rez_disponibil - 1;
    }

    @Override
    public void afisareProfil() {
        super.afisareProfil();
        System.out.println("An studiu: " + An_Studiu);
        System.out.println("Grupa: " + grupa);
        System.out.println("Numar Matricol: " + Nr_Matricol);
        System.out.println("Mai ai la dispozitie " + nr_rez_disponibil + " rezervari acesta luna.");

    }
}
