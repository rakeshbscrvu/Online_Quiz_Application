// TimerThread.java - simple countdown thread that updates JavaFX UI using Platform.runLater
import javafx.application.Platform;
import java.util.function.IntConsumer;

public class TimerThread extends Thread {
    private final int totalSeconds;
    private volatile boolean running = true;
    private final IntConsumer onTick; // receives remaining seconds
    private final Runnable onTimeUp;

    public TimerThread(int totalSeconds, IntConsumer onTick, Runnable onTimeUp) {
        this.totalSeconds = totalSeconds;
        this.onTick = onTick;
        this.onTimeUp = onTimeUp;
    }

    public void stopTimer() {
        running = false;
        this.interrupt();
    }

    @Override
    public void run() {
        int remaining = totalSeconds;
        try {
            while (running && remaining >= 0) {
                final int r = remaining;
                Platform.runLater(() -> onTick.accept(r));
                if (remaining == 0) break;
                Thread.sleep(1000);
                remaining--;
            }
        } catch (InterruptedException ignored) {}
        if (running) Platform.runLater(onTimeUp);
    }
}
