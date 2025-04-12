package models;
// Rezervare (id, sala, utilizator, data, interval_orar)

import java.util.HashMap;
import java.util.Vector;

public class Rezervare {
    private static int nr = 0;
    private int id;
    private int zi;
    private String ora;
    private Sala sala;
    private Utilizator utilizator;
    private HashMap<Integer,Vector< String> > data = new HashMap<>() ;

    public int getId() {
        return id;
    }

    public String getOra() {
        return ora;
    }

    public int getZi() {
        return zi;
    }

    public HashMap<Integer, Vector< String> > getData() {
        return data;
    }

    public Utilizator getUtilizator(){
        return utilizator;
    }

    public Sala getSala(){
        return sala;
    }

    private boolean validateInterval(Sala sala, int zi, String interval){
        Vector<Rezervare> rezervari = sala.getRez();

        for(Rezervare rezevare : rezervari){
            data = rezevare.getData();
            if(data.containsKey(zi)){
                for(String r : data.get(zi)){
                // ore stocate
                String intervalStocat = r;

                String[] ore = intervalStocat.split("-");
                int startD = convertToMinutes(ore[0]); // 13:00 → 780 minute
                int endD = convertToMinutes(ore[1]);   // 14:30 → 900 minute
        
                // (ex: 12:00 - 13:00)
                String intervalNou = interval;
                String[] ore_i = intervalNou.split("-");
                int startI = convertToMinutes(ore_i[0]); // 12:00 → 720 minute
                int endI = convertToMinutes(ore_i[1]);   // 13:30 → 840 minute
                
                // Verificăm dacă există suprapunere

                // D: 13:00-14:30, I: 14:00-15:30
                if (startI <= endD && startD < startI) {
                    System.out.println("❌ Suprapunere detectată!");
                    return false;
                }

                // D: 13:00-14:30, I: 12:00-13:30
                if (endI > startD && endI < endD) {
                    System.out.println("❌ Suprapunere detectată!");
                    return false;
                }
                //                    
                // D: 13:00-14:30, I: 13:30-14:00
                if (startI >= startD && endI <= endD) {
                    System.out.println("❌ Suprapunere detectată!");
                    return false;
                }
            }
        }
        
        }
        return true;
    }
        // Funcție care convertește "HH:mm" în minute totale
    private static int convertToMinutes(String ora) {
            String[] parts = ora.split(":");
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            return h * 60 + m;
    }


    public void afiseazaRezervare() {
        System.out.println("Rezervare în sala " + this.getSala().getNume() +
                " pe data de " + this.getZi() +
                " între " + this.getOra());
    }

    public Rezervare(Sala sala, Utilizator utilizator, Integer zi, String interval) {
        // stocam toate rezervarile salii din baza de date pentru a verifica daca se intersecteaza intervale
        if(validateInterval(sala, zi , interval)){
        id = nr++;
        this.sala = sala;
        this.utilizator = utilizator;
        this.ora = interval;
        this.zi = zi;

        if (!data.containsKey(zi)) {
            data.put(zi, new Vector<String>());
        }
        data.get(zi).add(interval);
        sala.addRez(this);
        System.out.println("Rezervare: " + id + " in " + sala.getNume());

        }
    }

}
