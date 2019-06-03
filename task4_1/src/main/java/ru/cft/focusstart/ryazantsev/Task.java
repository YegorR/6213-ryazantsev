package ru.cft.focusstart.ryazantsev;

import java.util.concurrent.atomic.AtomicLong;

import static ru.cft.focusstart.ryazantsev.Function.calcFunction;

public class Task implements Runnable {
    private long startValue;
    private long finishValue;

    static private AtomicLong result = new AtomicLong(0);

    Task(long startValue, long finishValue) {
        this.startValue = startValue;
        this.finishValue = finishValue;
    }

    @Override
    public void run() {
        long sum = 0;
        for (long i = startValue; i <= finishValue; ++i) {
            sum += calcFunction(i);
        }
        result.addAndGet(sum);
    }

    static long getResult() {
        return result.get();
    }


}
