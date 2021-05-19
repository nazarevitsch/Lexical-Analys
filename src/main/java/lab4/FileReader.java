package lab4;

import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    public List<String> readFile(String path) {
        List<String> lines = null;
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(new File(path)))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("File NOT FOUNT Exception!");
            System.exit(1);
        }
        return lines;
    }
}
