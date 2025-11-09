// MainApp.java - JavaFX entry (Phase 3)
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.List;

public class MainApp extends Application {
    private static final String CSV_PATH = "resources/questions.csv";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileQuestionLoader loader = new FileQuestionLoader();
        List<Question> questions = loader.loadQuestions(CSV_PATH);
        QuizManager manager = new QuizManager(questions);

        BorderPane root = new BorderPane();
        QuizController quizController = new QuizController(manager, root, primaryStage);
        quizController.showQuizScene();

        Scene scene = new Scene(root, 700, 450);
        scene.getStylesheets().add("file:resources/style.css");
        primaryStage.setTitle("Quiz App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
