package ru.cft.focusstart.ryazantsev.logic;

import java.util.Random;

import static ru.cft.focusstart.ryazantsev.logic.CellValues.*;

public class FieldCreator {
    private IntCouple size;
    private IntCouple initialCell;
    private int expectedMines;
    private int[][] field;

    public FieldCreator(IntCouple size, IntCouple initialCell, int expectedMines) {
        this.size = size;
        this.initialCell = initialCell;
        this.expectedMines = expectedMines;
        field = new int[size.getX()][size.getY()];
        createField();
    }

    public int[][] getField() {
        return field;
    }

    private void createField() {
        setMines();
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

    private void setMines() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < expectedMines; ++i) {
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

