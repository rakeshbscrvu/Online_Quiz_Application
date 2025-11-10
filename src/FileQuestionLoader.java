// FileQuestionLoader.java - reads CSV using Scanner
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileQuestionLoader {
    public List<Question> loadQuestions(String path) throws Exception {
        List<Question> list = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) throw new Exception("File not found: " + path);
        try (Scanner sc = new Scanner(file, "UTF-8")) {
            if (!sc.hasNextLine()) throw new Exception("Empty CSV");
            sc.nextLine(); // skip header
            int lineNo = 1;
            while (sc.hasNextLine()) {
                lineNo++;
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 8) throw new Exception("Bad CSV at line " + lineNo);
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String q = unquote(parts[1].trim());
                    String a = unquote(parts[2].trim());
                    String b = unquote(parts[3].trim());
                    String c = unquote(parts[4].trim());
                    String d = unquote(parts[5].trim());
                    char correct = unquote(parts[6].trim()).charAt(0);
                    int time = Integer.parseInt(parts[7].trim());
                    list.add(new Question(id, q, a, b, c, d, correct, time));
                } catch (Exception e) {
                    throw new Exception("Parse error line " + lineNo + ": " + e.getMessage());
                }
            }
        }
        return list;
    }

    private String unquote(String s) {
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) return s.substring(1, s.length()-1);
        return s;
    }
}
