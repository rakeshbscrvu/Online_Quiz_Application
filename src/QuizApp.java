// QuizApp.java - console quiz (Phase 1). Run this first to check CSV parsing.
import java.util.List;
import java.util.Scanner;

public class QuizApp {
    public static void main(String[] args) {
        String csvPath = "resources/questions.csv";
        FileQuestionLoader loader = new FileQuestionLoader();
        try {
            List<Question> questions = loader.loadQuestions(csvPath);
            QuizManager manager = new QuizManager(questions);
            runConsoleQuiz(manager);
        } catch (Exception e) {
            System.err.println("Failed to load questions: " + e.getMessage());
        }
    }

    private static void runConsoleQuiz(QuizManager manager) {
        Scanner in = new Scanner(System.in);
        System.out.println("=== Console Quiz ===");
        for (int i = 0; i < manager.size(); i++) {
            manager.goTo(i);
            Question q = manager.getCurrentQuestion();
            System.out.println("\nQ" + (i+1) + ": " + q.getQuestion());
            System.out.println("A) " + q.getOptionA());
            System.out.println("B) " + q.getOptionB());
            System.out.println("C) " + q.getOptionC());
            System.out.println("D) " + q.getOptionD());
            System.out.print("Your answer (A/B/C/D) : ");
            String ans = in.nextLine().trim();
            if (ans.isEmpty()) manager.setAnswerForCurrent('\0');
            else manager.setAnswerForCurrent(ans.charAt(0));
        }
        int score = manager.calculateScore();
        System.out.println("\nFinished! Score: " + score + " / " + manager.size());
        System.out.println("Review:");
        for (int idx = 0; idx < manager.size(); idx++) {
            manager.goTo(idx);
            Question q = manager.getCurrentQuestion();
            char user = manager.getAnswer(idx);
            System.out.println((idx+1) + ". " + q.getQuestion() + " | Your: " + (user == '\0' ? "-" : user) + " | Correct: " + q.getCorrectOption());
        }
    }
}
