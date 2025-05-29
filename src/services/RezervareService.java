package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import models.*;

public class RezervareService {

    private static RezervareService instance;
    private final Connection connection;
    private final AuditService audit = AuditService.getInstance();

    // clasa singleton
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
    // Rezervare(zi, ora, sala_id, student_id, profesor_id)

    public boolean adaugaRezervareStudent(int zi, String interval, int sala_id, int student_id ) {
        // cream obiect validareRezervare pentru a valida intervalul
        ValidareRezervare v = new ValidareRezervare();
        if(v.validateInterval(sala_id, zi , interval)){
            
            String sql = "INSERT INTO rezervare (zi, ora, sala_id, student_id, profesor_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, zi);
                stmt.setString(2, interval);
                stmt.setInt(3, sala_id);
                stmt.setInt(4, student_id - 1);
                stmt.setNull(5, java.sql.Types.INTEGER); // setam null

                stmt.executeUpdate();
                System.out.println("Rezervare adăugată!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            audit.logAction("Adaugare Rezervare Noua Student");
            return true;
        }
        return false;
    }

    public boolean adaugaRezervareProfesor(int zi, String interval, int sala_id, int profesor_id ) {
        // cream obiect validareRezervare pentru a valida intervalul
        ValidareRezervare v = new ValidareRezervare();
        if(v.validateInterval(sala_id, zi , interval)){

            String sql = "INSERT INTO rezervare (zi, ora, sala_id, student_id, profesor_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, zi);
                stmt.setString(2, interval);
                stmt.setInt(3, sala_id);
                stmt.setNull(4, java.sql.Types.INTEGER); // setam null
                stmt.setInt(5, profesor_id - 1);

                stmt.executeUpdate();
                System.out.println("Rezervare adăugată!");
                audit.logAction("Adaugare Rezervare Noua Profesor");

            } catch (SQLException e) {
                e.printStackTrace();
            }
            audit.logAction("Adaugare Rezervare Noua Profesor");
            return true;
        }
        return false;
    }

    public void adaugaSala(String nume, int capacitate){
        String sql = "INSERT INTO sala (nume,capacitate) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nume);
            stmt.setInt(2, capacitate);
            
            stmt.executeUpdate();
            System.out.println("Sala adăugată!");
            audit.logAction("Adaugare sala noua");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void creareContStudent(String nume, String email,int an_studiu, int nr_matricol, int facultate_id) {
        String sql = "INSERT INTO student (nume, email, an_studiu, nr_matricol, facultate_id, nr_rez_disponibil) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nume);
            stmt.setString(2, email);
            stmt.setInt(3, an_studiu);
            stmt.setInt(4, nr_matricol);
            stmt.setInt(5, facultate_id);
            stmt.setInt(6, 3); // numarul de rezervari disponibile
            stmt.executeUpdate();
            System.out.println("Cont student creat!");
            audit.logAction("Cont de student creat");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void creareContProfesor(String nume, String email, String departament, int facultate_id) {
        String sql = "INSERT INTO profesor (nume, email, departament, facultate_id) VALUES (?, ?, ?, ?)";
        String sql1 = "select id from profesor where nume = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nume);
            stmt.setString(2, email);
            stmt.setString(3, departament);
            stmt.setInt(4, facultate_id);

            stmt.executeUpdate();
            try (PreparedStatement stmt1 = connection.prepareStatement(sql1)){
                stmt1.setString(1,nume);
                ResultSet rs = stmt1.executeQuery();
                rs.next();
                System.out.println("ID-ul tau este : " + rs.getInt("id"));
            }catch(SQLException e){
                e.printStackTrace();
            }
            System.out.println("Cont de profesor creat!");
            audit.logAction("Cont de profesor creat");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // READ
    public void afiseazaRezervari() {
        String sql = "SELECT * FROM rezervare";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int zi = rs.getInt("zi");
                String ora = rs.getString("ora");
                int id_sala = rs.getInt("sala_id");
                String IDStudent = rs.getString("student_id");
                String IDProfesor = rs.getString("profesor_id");

                String nume = "Necunoscut";
                String querry;

                if (IDStudent != null) {
                    querry = "SELECT nume FROM student WHERE id = ?";
                } else {
                    querry = "SELECT nume FROM profesor WHERE id = ?";
                }

                try (PreparedStatement stmt1 = connection.prepareStatement(querry)) {

                    if (IDStudent != null) {
                        stmt1.setInt(1, Integer.parseInt(IDStudent));
                        nume = "Student: ";
                    } else {
                        nume = "Profesor: ";
                        stmt1.setInt(1, Integer.parseInt(IDProfesor));
                    }

                    ResultSet rs1 = stmt1.executeQuery();
                    if (rs1.next()) {
                            nume += rs1.getString("nume");
                        }

                }

                System.out.println("ID Rezervare: " + id + ", Zi: " + zi + ", ora: " + ora +
                        ", ID_Sala: " + id_sala +
                        "," + nume);
                audit.logAction("Cerere de afisare rezervari");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afiseazaRezervariSala(int ID) {
        String sql = "SELECT * FROM rezervare where sala_id = " + ID;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int zi = rs.getInt("zi");
                String ora = rs.getString("ora");
                int id_sala = rs.getInt("sala_id");
                String IDStudent = rs.getString("student_id");
                String IDProfesor = rs.getString("profesor_id");

                String nume = "Necunoscut";
                String querry;

                if (IDStudent != null) {
                    querry = "SELECT nume FROM student WHERE id = ?";
                } else {
                    querry = "SELECT nume FROM profesor WHERE id = ?";
                }

                try (PreparedStatement stmt1 = connection.prepareStatement(querry)) {

                    if (IDStudent != null) {
                        stmt1.setInt(1, Integer.parseInt(IDStudent));
                        nume = "Student: ";
                    } else {
                        nume = "Profesor: ";
                        stmt1.setInt(1, Integer.parseInt(IDProfesor));
                    }

                    ResultSet rs1 = stmt1.executeQuery();
                    if (rs1.next()) {
                        nume += rs1.getString("nume");
                    }

                }

                System.out.println("ID Rezervare: " + id + ", Zi: " + zi + ", ora: " + ora +
                        ", ID_Sala: " + id_sala +
                        "," + nume);
                audit.logAction("Cerere de afisare rezervari ale unei sali");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void PrintSaliFacultate(Facultate f){

        int i = f.getId() + 1;
        String querry = "Select * from sala where facultate_id = "+ i ;
        try (PreparedStatement stmt = connection.prepareStatement(querry)) {
            ResultSet rs = stmt.executeQuery();
            int id;
            String nume ;
            int capacitate;
            String tip ;
            while (rs.next()) {

                id = rs.getInt("id");
                nume = rs.getString("nume");
                capacitate = rs.getInt("capacitate");
                tip = rs.getString("tip");
                System.out.println("ID: " + id);
                System.out.println("Nume: " + nume);
                System.out.println("Capacitate: " + capacitate);
                System.out.println("Tip: " + tip + "\n");
                audit.logAction("Cerere de afisare sali dintr-o facultate");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    };

    public Vector<Student> getStudents(){
        String sql = "SELECT * FROM student";
        Vector<Student> students = new Vector<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                String email = rs.getString("email");
                int an_studiu = rs.getInt("an_studiu");
                int nr_matricol = rs.getInt("nr_matricol");
                int facultate_id = rs.getInt("facultate_id");
                int nr_rez_disponibil = rs.getInt("nr_rez_disponibil");
                int grupa = rs.getInt("grupa");
                Student student = new Student(id,nume, email,facultate_id, an_studiu,grupa, nr_matricol, nr_rez_disponibil);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Vector<Profesor> getProfesors(){
        String sql = "SELECT * FROM profesor";
        Vector<Profesor> profesors = new Vector<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                String email = rs.getString("email");
                String dept = rs.getString("departament");
                int facultate_id = rs.getInt("facultate_id");

                Profesor student = new Profesor(nume, email,facultate_id, dept,id);
                profesors.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profesors;
    }


    // adaugam toate rezervarile salii in hashmap
    public HashMap<Integer,Vector< String> > RezervariSala(int ID) {
        String sql = "SELECT * FROM rezervare where sala_id = " + ID;
        HashMap<Integer,Vector< String> > data = new HashMap<>() ;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int zi = rs.getInt("zi");
                String ora = rs.getString("ora");

                if (!data.containsKey(zi)) {
                    data.put(zi, new Vector<>());
                }
                data.get(zi).add(ora);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Vector<Facultate> getFacultati(){
        Vector<Facultate> facultati = new Vector<>();
        String sql = "SELECT * FROM facultate";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                Facultate f = new Facultate(nume);
                facultati.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facultati;
    };

    public Vector<Sala> getSali(){
        Vector<Sala> v = new Vector<>();

        String querry = "Select * from sala ";
        try (PreparedStatement stmt = connection.prepareStatement(querry)) {
            ResultSet rs = stmt.executeQuery();
            int id;
            String nume ;
            int capacitate;
            String tip ;
            while (rs.next()) {

                id = rs.getInt("id");
                nume = rs.getString("nume");
                capacitate = rs.getInt("capacitate");
                tip = rs.getString("tip");
                // Adaugă orice alte câmpuri relevante
                if(tip.equalsIgnoreCase( "seminar")){
                    Sala_Seminar sala = new Sala_Seminar(nume, capacitate);
                    v.add(sala);
                } else if (tip.equalsIgnoreCase("laborator")) {
                    Sala_Laborator sala = new Sala_Laborator(nume, capacitate);
                    v.add(sala);

                }else{
                    Sala_Amfiteatru sala = new Sala_Amfiteatru(nume, capacitate);
                    v.add(sala);

                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return v;
    };

    public Vector<Sala> getSaliFacultate(Facultate f){
      Vector<Sala> v = new Vector<>();
      int i = f.getId() + 1;
      String querry = "Select * from sala where facultate_id = "+ i ;
        try (PreparedStatement stmt = connection.prepareStatement(querry)) {
            ResultSet rs = stmt.executeQuery();
            int id;
            String nume ;
            int capacitate;
            String tip ;
            while (rs.next()) {

                id = rs.getInt("id");
                nume = rs.getString("nume");
                capacitate = rs.getInt("capacitate");
                tip = rs.getString("tip");
                // Adaugă orice alte câmpuri relevante
                Sala sala = new Sala(nume, capacitate);
                v.add(sala);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return v;
    };

    public void getRezervariStudent(Student s) {
        String sql = "SELECT * FROM rezervare WHERE student_id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, s.getId()); // Presupunem că Utilizator are un getId()
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int zi = rs.getInt("zi");
                String ora = rs.getString("ora");
                int idSala = rs.getInt("sala_id");

                System.out.println("Rezervare ID: " + id);
                System.out.println("Zi: " + zi);
                System.out.println("Ora: " + ora);
                System.out.println("ID Sala: " + idSala);
                System.out.println("-----------------------");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Vector<Integer> afiseazaRezervariPersoana(String tip, int id){
        String sql;
        Vector<Integer> r = new Vector<>();
        if(tip.equalsIgnoreCase("profesor")){
            sql = "select * from rezervare where profesor_id = " + id;
        }else{
            sql = "select * from rezervare where student_id = " + id;
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            int id_Rez;
            int zi;
            String ora ;
            int sala_id;
            int i = 1;
            while (rs.next()) {
                id_Rez = rs.getInt("id");
                zi = rs.getInt("zi");
                ora = rs.getString("ora");
                sala_id = rs.getInt("sala_id");

                System.out.println("Rezervarea #"+ i);
                System.out.println("Sala: " + sala_id);
                System.out.println("Zi: " + zi);
                System.out.println("Ora: " + ora + "\n");
                System.out.println("-----------------------");
                i = i + 1;
                r.add(id_Rez);
                audit.logAction("Cerere de afisare a rezervarilor unui cont");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return r;
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

    public void actualizareNrRezervariDisponibile(int id_student) {
        String sql = "UPDATE student SET nr_rez_disponibil = nr_rez_disponibil - 1 WHERE id = ? AND nr_rez_disponibil > 0";
        String sql1 = "Select nr_rez_disponibil from student where id = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1, id_student);
            int affectedRows = stmt.executeUpdate();
            try(PreparedStatement stmt1 = connection.prepareStatement(sql1)){
                stmt1.setInt(1, id_student);
                ResultSet rs = stmt1.executeQuery();
                if(rs.next()){
                    System.out.println("Mai ai la dispozitie "+rs.getInt("nr_rez_disponibil") + " rezervari");
                }

            }
            if (affectedRows == 0) {
                System.out.println("Studentul nu are rezervări disponibile sau nu există.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void stergeRezervare(int id) {
        String sql = "DELETE FROM rezervare WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Rezervare ștearsă!");
            audit.logAction("Cerere de stergere a unei rezervari");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
