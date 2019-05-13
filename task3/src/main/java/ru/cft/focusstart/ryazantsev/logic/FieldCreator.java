package ru.cft.focusstart.ryazantsev.logic;

import ru.cft.focusstart.ryazantsev.util.IntCouple;

import java.util.Random;

import static ru.cft.focusstart.ryazantsev.util.CellValues.*;

public class FieldCreator {
    private IntCouple size;
    private int mines;
    private int[][] field;

    public FieldCreator(IntCouple size, int mines) {
        this.size = size;
        this.mines = mines;
        field = new int[size.getX()][size.getY()];
    }

    public int[][] getField() {
        return field;
    }

    public int getMines() {
        return mines;
    }

    public void createField(IntCouple initialCell) {
        setMines(initialCell);
        for (int i = 0; i < size.getX(); ++i) {
            for (int j = 0; j < size.getY(); ++j) {
                if (field[i][j] == ZERO) {
                    field[i][j] = countMinesAround(i, j);
                }
            }
        }
    }

    private int countMinesAround(int row, int column) {
        int result = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if ((i == 0) && (j == 0)) {
                    continue;
                }
                try {
                    if (field[row + i][column + j] == MINE) {
                        result++;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        return result;
    }

    private void setMines(IntCouple initialCell) {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < mines; ++i) {
            while (true) {
                int row = random.nextInt(size.getX());
                int column = random.nextInt(size.getY());
                if ((field[row][column] == ZERO) && !((Math.abs(row - initialCell.getX()) <= 1) &&
                        ((Math.abs(column - initialCell.getY())) <= 1))) {
                    field[row][column] = MINE;
                    break;
                }
            }
        }
    }
}

