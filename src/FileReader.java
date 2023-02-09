import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Collections;

class FileReader {
    String errorMessage = "";

    FileReader(String message) {
        errorMessage = message;
    }

    List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println(errorMessage);
            return Collections.emptyList();
        }
    }
}