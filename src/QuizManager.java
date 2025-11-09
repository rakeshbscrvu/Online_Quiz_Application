// QuizManager.java - manages questions and user answers
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizManager {
    private final List<Question> questions;
    private final List<Character> userAnswers;
    private int currentIndex = 0;

    public QuizManager(List<Question> questions) {
        this.questions = new ArrayList<>(questions);
        this.userAnswers = new ArrayList<>(Collections.nCopies(this.questions.size(), '\0'));
    }

    public int size() { return questions.size(); }
    public Question getCurrentQuestion() { return questions.get(currentIndex); }
    public int getCurrentIndex() { return currentIndex; }
    public void next() { if (currentIndex < questions.size() - 1) currentIndex++; }
    public void previous() { if (currentIndex > 0) currentIndex--; }
    public void goTo(int index) { if (index >= 0 && index < questions.size()) currentIndex = index; }

    public void setAnswer(int index, char option) {
        if (index >= 0 && index < userAnswers.size()) userAnswers.set(index, Character.toUpperCase(option));
    }
    public void setAnswerForCurrent(char option) { setAnswer(currentIndex, option); }
    public char getAnswer(int index) { return userAnswers.get(index); }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            char user = userAnswers.get(i);
            if (user != '\0' && user == questions.get(i).getCorrectOption()) score++;
        }
        return score;
    }

    public List<Integer> wrongAnswerIndices() {
        List<Integer> wrong = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            char user = userAnswers.get(i);
            if (user == '\0' || user != questions.get(i).getCorrectOption()) wrong.add(i);
        }
        return wrong;
    }
}
