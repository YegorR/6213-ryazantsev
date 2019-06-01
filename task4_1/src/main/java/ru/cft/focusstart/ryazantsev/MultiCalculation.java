package ru.cft.focusstart.ryazantsev;

import java.util.ArrayList;
import java.util.List;

public class MultiCalculation {
    private final static long N = 1_000_000;

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i < N; i += 100_000) {
            threads.add(new Thread(new Task(i, Math.min(i + 99_999, N))));
        }
        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
            System.err.println("Поток был прерван.\nЭто исключение не должно сгенерироваться...");
            return;
        }
        System.out.println("Вычисленная сумма:\n" + Task.getResult());
    }
}
