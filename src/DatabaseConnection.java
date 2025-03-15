import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/PAO"; // Schimbă cu numele corect al bazei de date
    private static final String USER = "clau"; 
    private static final String PASSWORD = "nykolas23"; 
    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Încărcarea driverului PostgreSQL (opțional pentru versiunile noi de Java)
                Class.forName("org.postgresql.Driver");
                
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexiune reușită la PostgreSQL!");
            } catch (ClassNotFoundException e) {
                System.out.println("Driverul PostgreSQL nu a fost găsit!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Eroare la conectarea la baza de date!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}


