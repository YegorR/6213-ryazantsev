package ru.cft.focusstart.ryazantsev.util;

import java.util.Objects;

public class IntCouple {
    private final int x;
    private final int y;

    public IntCouple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntCouple intCouple = (IntCouple) o;
        return x == intCouple.x &&
                y == intCouple.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
