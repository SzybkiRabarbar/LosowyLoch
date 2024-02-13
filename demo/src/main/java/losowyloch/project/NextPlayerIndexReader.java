package losowyloch.project;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.net.URISyntaxException;

public class NextPlayerIndexReader {
    private static final String JSON_FILE_PATH;

    static {
        String tempPath = null;
        URL res = NextPlayerIndexReader.class.getClassLoader().getResource("playerindex.json");
        if (res == null) {
            throw new IllegalStateException("Cannot find file: playerindex.json");
        } else {
            try {
                tempPath = Paths.get(res.toURI()).toFile().getAbsolutePath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } finally {
                if (tempPath == null) {
                    throw new IllegalStateException("JSON_FILE_PATH has not been initialized");
                } else {
                    JSON_FILE_PATH = tempPath;
                }
            }
        }
    }

    public static int readIndex() {
        int number = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                number = Integer.parseInt(line);
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas odczytu pliku.");
        }
        writeNumber(number + 1);
        return number;
    }

    private static void writeNumber(int number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH))) {
            writer.write(String.valueOf(number));
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisu do pliku.");
        }
    }
}
