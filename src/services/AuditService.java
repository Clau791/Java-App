package services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class AuditService {

    private static final String FILE_NAME = "audit.csv";
    private static final Path FILE_PATH = Paths.get(FILE_NAME);
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final AuditService INSTANCE = new AuditService();

    private AuditService() {
        // dacă fișierul nu există, îl creăm și adăugăm header-ul
        if (Files.notExists(FILE_PATH)) {
            try {
                Files.createFile(FILE_PATH);
                try (BufferedWriter bw = Files.newBufferedWriter(FILE_PATH,
                        StandardOpenOption.APPEND)) {
                    bw.write("nume_actiune,timestamp");
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Nu pot crea fișierul de audit", e);
            }
        }
    }

    public static AuditService getInstance() {
        return INSTANCE;
    }

    /**
     * Înregistrează o acțiune în audit.csv, linie nouă: nume_actiune,timestamp
     * Metoda este synchronized ⇒ sigură în multi-thread.
     */
    public synchronized void logAction(String actionName) {
        String line = actionName + "," + LocalDateTime.now().format(FORMAT);
        try (BufferedWriter bw = Files.newBufferedWriter(FILE_PATH,
                StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Eroare la scrierea în audit.csv: " + e.getMessage());
        }
    }
}
