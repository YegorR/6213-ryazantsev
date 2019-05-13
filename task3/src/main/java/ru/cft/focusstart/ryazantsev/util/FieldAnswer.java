package ru.cft.focusstart.ryazantsev.util;

import java.util.Map;
import java.util.Objects;

public class FieldAnswer {
    private Map<IntCouple, ViewCellValue> cells;
    private GameStatus gameStatus;
    private int flags;

    public FieldAnswer(Map<IntCouple, ViewCellValue> cells, GameStatus gameStatus, int flags) {
        this.cells = cells;
        this.gameStatus = gameStatus;
        this.flags = flags;
    }

    public Map<IntCouple, ViewCellValue> getCells() {
        return cells;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getFlags() {
        return flags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldAnswer that = (FieldAnswer) o;
        return flags == that.flags &&
                Objects.equals(cells, that.cells) &&
                gameStatus == that.gameStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells, gameStatus, flags);
    }
}
