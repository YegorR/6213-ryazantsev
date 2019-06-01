package ru.cft.focusstart.ryazantsev;

public class Function {
    static long calcFunction(long arg) {
        long result = 0;
        for (int i = 1; i <= 500; ++i) {
            result += i * arg * Math.pow(-1, i);
        }
        return result;
    }
}
