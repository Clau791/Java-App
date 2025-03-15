

public class Main {

    public static void main(String[] args) {
        //PersonService personService = new PersonService();
        RezervareService c_sala = RezervareService.getInstance();
        // c_sala.afiseazaRezervariSala(1);
        
        // -> apeleaza adaugaRez din
        //    RezervareService -> ia toate rezervarile curente salii -> verifica daca are intervale suprapuse
        c_sala.adaugaRezervare(1, "marcel", 1, "13:00-14:30");

        // c_sala.adaugaRezervare(1, "Claudiu", 1, "12:00-13:00");
        // c_sala.adaugaRezervare(2, "Claudiu", 1, "12:00-13:00");
        // c_sala.adaugaRezervare(3, "Claudiu", 2, "14:00-15:00");
    }
}


// 
// 📌 Obiecte principale

// Sala (id, nume, capacitate, rezervari)
// Amfiteatru (id, nume, capacitate, disponibilitate) - derivat din Sala
// Rezervare (id, sala, utilizator, data, interval_orar)
// Utilizator (id, nume, tip utilizator - student/profesor/admin)
// Eveniment (id, titlu, sală, organizator, descriere)
// Facultate(id, nume, sali )

// 📌 Acțiuni posibile
// ✔️ Căutare săli disponibile
// ✔️ Creare rezervare
// ✔️ Anulare rezervare
// ✔️ Vizualizare istoric rezervări
// ✔️ Gestionare drepturi utilizatori (ex: admin poate bloca săli)