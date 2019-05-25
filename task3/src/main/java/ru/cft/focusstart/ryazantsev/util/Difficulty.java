package ru.cft.focusstart.ryazantsev.util;

public enum Difficulty {
    EASY(new IntCouple(10, 10), 16),
    MIDDLE(new IntCouple(15, 15), 45),
    HARD(new IntCouple(20, 20), 65);

    private IntCouple size;
    private int mineCount;

    Difficulty(IntCouple size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
    }

    public IntCouple getSize() {
        return size;
    }

    public int getMineCount() {
        return mineCount;
    }
}