// Question.java - model
public class Question {
    private final int id;
    private final String question;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final char correctOption; // 'A','B','C','D'
    private final int timeLimitSeconds;

    public Question(int id, String question,
                    String optionA, String optionB, String optionC, String optionD,
                    char correctOption, int timeLimitSeconds) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = Character.toUpperCase(correctOption);
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public int getId() { return id; }
    public String getQuestion() { return question; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public char getCorrectOption() { return correctOption; }
    public int getTimeLimitSeconds() { return timeLimitSeconds; }
}
