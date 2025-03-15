public class Add {
    public static void main(String[] args) {

        //adaugare sali / rezervari
        Sala sala1 = new Sala("Sala-1", 25);
        sala1.displaySala();

        Sala sala2 = new Sala("Sala-3", 35);
        sala2.displaySala();
        
        Rezervare rezervare1 = new Rezervare(sala1, "Claudiu", 1, "13:00-14:30");
        sala1.addRez(rezervare1);

        Rezervare rezervare3 = new Rezervare(sala1, "Claudiu", 1, "12:00-13:00");
        sala1.addRez(rezervare3);

        Rezervare rezervare4 = new Rezervare(sala2, "Claudiu", 1, "14:00-15:30");
        sala2.addRez(rezervare4);

        sala1.displaySala();
    }
}
