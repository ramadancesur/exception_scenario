import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class Main {

    // 1. Custom Exception oluşturun
    static class FileReadException extends Exception {
        public FileReadException(String message) {
            super(message);
        }
    }

    // 2. Hata metodu - Dosya okuma işlemi
    public static String readFile(String filePath) throws FileReadException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new FileReadException("Dosya okuma hatası: " + e.getMessage());
        }
        return content.toString();
    }

    public static void main(String[] args) {
        // 3. Test senaryosu oluşturun
        try {
            // Dosya okuma başarılı
            String content = readFile("test.txt");
            System.out.println("Dosya İçeriği: \n" + content);

            // Geçersiz dosya yolu - hata durumu
            content = readFile("gecersiz_dosya.txt");
            System.out.println("Dosya İçeriği: \n" + content);
        } catch (FileReadException e) {
            // Hata mesajını yakalama
            System.err.println("Hata: " + e.getMessage());
        }
    }
}

// Test Sınıfı

class CustomFileReaderExampleTest {

    @Test
    public void testFileReadException() {
        Exception exception = assertThrows(Main.FileReadException.class, () -> {
            Main.readFile("gecersiz_dosya.txt");
        });

        String expectedMessage = "Dosya okuma hatası";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFileReadSuccessful() {
        try {
            // Bu testin çalışması için "test.txt" dosyasının mevcut olduğundan emin olun.
            String content = Main.readFile("test.txt");
            assertNotNull(content);
        } catch (Main.FileReadException e) {
            fail("Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }
}

