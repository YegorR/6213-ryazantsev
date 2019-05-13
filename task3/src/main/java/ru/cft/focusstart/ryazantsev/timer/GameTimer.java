package ru.cft.focusstart.ryazantsev.timer;


import javax.swing.*;
import java.util.function.Consumer;

public class GameTimer {
    private Timer timer;
    private int seconds = 0;
    private Consumer<Integer> timerMethod;

    public GameTimer(Consumer<Integer> timerMethod) {
        timer = new Timer(1000, e -> action());
        this.timerMethod = timerMethod;
    }

    public void run() {
        seconds = 0;
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public int getSeconds() {
        return seconds;
    }

    private void action() {
        if (seconds >= 999) {
            timer.stop();
            return;
        }
        seconds++;
        timerMethod.accept(seconds);
    }
}
