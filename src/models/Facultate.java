import java.util.Vector;
public class Facultate {
    private int id ;
    private static int contor = 0;
    private String nume ;
    private Vector<Sala> sali = new Vector<>();

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public Vector<Sala> getSali(){
        return sali;
    }

    void addSala(Sala sala){
        sali.add(sala);
    }

    public Facultate(String nume, Vector<Sala> sali){
        this.id = contor++;
        this.nume = nume;
        this.sali = sali;
    }
}
