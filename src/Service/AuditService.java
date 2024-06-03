package Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {

    private static AuditService instance;
    private FileWriter writer;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AuditService() throws IOException {
        this.writer = new FileWriter("C:\\Anul 2\\SEM 2\\PAO\\proiect\\src\\audit.csv", true);
    }

    public static synchronized AuditService getInstance() throws IOException {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public static synchronized void logAction(String action) {
        try {
            writer.append(action);
            writer.append(",");
            writer.append(formatter.format(LocalDateTime.now()));
            writer.append("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
