// QuizController.java - JavaFX UI and event handling
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.function.IntConsumer;

public class QuizController {
    private final QuizManager manager;
    private final BorderPane root;
    private final Stage stage;

    private Label lblQuestion;
    private RadioButton rA, rB, rC, rD;
    private ToggleGroup group;
    private Label lblTimer;
    private Button btnNext, btnPrev, btnSubmit;
    private TimerThread timerThread;

    public QuizController(QuizManager manager, BorderPane root, Stage stage) {
        this.manager = manager;
        this.root = root;
        this.stage = stage;
    }

    public void showQuizScene() {
        lblQuestion = new Label();
        lblQuestion.setWrapText(true);
        rA = new RadioButton();
        rB = new RadioButton();
        rC = new RadioButton();
        rD = new RadioButton();
        group = new ToggleGroup();
        rA.setToggleGroup(group); rB.setToggleGroup(group); rC.setToggleGroup(group); rD.setToggleGroup(group);

        lblTimer = new Label("Time: --");
        btnNext = new Button("Next");
        btnPrev = new Button("Prev");
        btnSubmit = new Button("Submit");

        btnNext.setOnAction(e -> { saveSelection(); manager.next(); loadQuestion(); });
        btnPrev.setOnAction(e -> { saveSelection(); manager.previous(); loadQuestion(); });
        btnSubmit.setOnAction(e -> { saveSelection(); stopTimer(); showResultScene(); });

        VBox vbox = new VBox(10, lblTimer, lblQuestion, rA, rB, rC, rD, new Separator(), new ToolBar(btnPrev, btnNext, btnSubmit));
        vbox.setPadding(new Insets(12));
        root.setCenter(vbox);

        loadQuestion();
        startTimerForCurrentQuestion();
    }

    private void loadQuestion() {
        Question q = manager.getCurrentQuestion();
        lblQuestion.setText((manager.getCurrentIndex()+1) + ". " + q.getQuestion());
        rA.setText("A) " + q.getOptionA());
        rB.setText("B) " + q.getOptionB());
        rC.setText("C) " + q.getOptionC());
        rD.setText("D) " + q.getOptionD());
        char ans = manager.getAnswer(manager.getCurrentIndex());
        if (ans == 'A') group.selectToggle(rA);
        else if (ans == 'B') group.selectToggle(rB);
        else if (ans == 'C') group.selectToggle(rC);
        else if (ans == 'D') group.selectToggle(rD);
        else group.selectToggle(null);

        stopTimer();
        startTimerForCurrentQuestion();
    }

    private void saveSelection() {
        Toggle t = group.getSelectedToggle();
        if (t == null) { manager.setAnswerForCurrent('\0'); return; }
        Node node = (Node) t;
        if (node == rA) manager.setAnswerForCurrent('A');
        else if (node == rB) manager.setAnswerForCurrent('B');
        else if (node == rC) manager.setAnswerForCurrent('C');
        else if (node == rD) manager.setAnswerForCurrent('D');
    }

    private void startTimerForCurrentQuestion() {
        Question q = manager.getCurrentQuestion();
        int seconds = q.getTimeLimitSeconds();
        IntConsumer onTick = rem -> lblTimer.setText("Time left: " + rem + "s");
        Runnable onTimeUp = () -> {
            saveSelection();
            if (manager.getCurrentIndex() < manager.size() - 1) {
                manager.next(); loadQuestion();
            } else {
                showResultScene();
            }
        };
        timerThread = new TimerThread(seconds, rem -> onTick.accept(rem), onTimeUp);
        timerThread.start();
    }

    private void stopTimer() {
        if (timerThread != null && timerThread.isAlive()) timerThread.stopTimer();
    }

    private void showResultScene() {
        stopTimer();
        ResultController rc = new ResultController(manager, root, stage);
        rc.showResultScene();
    }
}
