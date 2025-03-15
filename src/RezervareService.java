import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class RezervareService {

    private static RezervareService instance;
    private Connection connection;

    private RezervareService() {
        this.connection = DatabaseConnection.getConnection();
    }

    public static RezervareService getInstance() {
        if (instance == null) {
            instance = new RezervareService();
        }
        return instance;
    }

    // CREATE
    public void adaugaRezervare(int sala_id,String utilizator, int zi, String interval) {
        String sql = "INSERT INTO rezervari (sala_id, zi, ora, nume_utilizator) VALUES (?, ?, ?, ?)";

        // cream obiect validareRezervare pentru a valida intervalul
        ValidareRezervare v = new ValidareRezervare();
        if(v.validateInterval(sala_id, zi , interval)){
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, sala_id);
                stmt.setInt(2, zi);
                stmt.setString(3, interval);
                stmt.setString(4, utilizator);
                
                stmt.executeUpdate();
                System.out.println("Rezervare adăugată!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void adaugaSala(String nume, int capacitate){
        String sql = "INSERT INTO sala (nume,capacitate) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nume);
            stmt.setInt(2, capacitate);
            
            stmt.executeUpdate();
            System.out.println("Sala adăugată!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    public void afiseazaRezervari() {
        String sql = "SELECT * FROM rezervari";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("sala_id") +
                                ", Utilizator: " + rs.getString("nume_utilizator") +
                                ", Zi: " + rs.getInt("zi") +
                                ", Interval: " + rs.getString("ora"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void afiseazaRezervariSala(int ID) {
        String sql = "SELECT * FROM rezervari where sala_id = " + String.valueOf(ID);
        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + ID +
                                ", Utilizator: " + rs.getString("nume_utilizator") +
                                ", Zi: " + rs.getInt("zi") +
                                ", Interval: " + rs.getString("ora"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // adaugam toate rezervarile salii in hashmap
    public HashMap<Integer,Vector< String> > RezervariSala(int ID) {
        String sql = "SELECT * FROM rezervari where sala_id = " + String.valueOf(ID);
        HashMap<Integer,Vector< String> > data = new HashMap<>() ;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int zi = rs.getInt("zi");
                // String nume_utilizator = rs.getString("nume_utilizator");
                String ora = rs.getString("ora");

                if (!data.containsKey(zi)) {
                    data.put(zi, new Vector<String>());
                }
                data.get(zi).add(ora);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }



    // UPDATE
    public void actualizeazaRezervare(int id, String intervalNou) {
        String sql = "UPDATE rezervari SET interval = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, intervalNou);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Rezervare actualizată!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void stergeRezervare(int id) {
        String sql = "DELETE FROM rezervari WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Rezervare ștearsă!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
