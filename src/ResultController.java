// ResultController.java - show score and review
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ResultController {
    private final QuizManager manager;
    private final BorderPane root;
    private final Stage stage;

    public ResultController(QuizManager manager, BorderPane root, Stage stage) {
        this.manager = manager;
        this.root = root;
        this.stage = stage;
    }

    public void showResultScene() {
        int score = manager.calculateScore();
        Label l1 = new Label("Score: " + score + " / " + manager.size());
        ListView<String> lv = new ListView<>();
        for (int idx = 0; idx < manager.size(); idx++) {
            manager.goTo(idx);
            Question q = manager.getCurrentQuestion();
            char user = manager.getAnswer(idx);
            String line = (idx+1) + ". " + q.getQuestion() + " | Your: " + (user == '\0' ? "-" : user) + " | Correct: " + q.getCorrectOption();
            lv.getItems().add(line);
        }
        Button btnClose = new Button("Close");
        btnClose.setOnAction(e -> stage.close());
        VBox vbox = new VBox(10, l1, lv, btnClose);
        vbox.setPadding(new Insets(12));
        root.setCenter(vbox);
    }
}
