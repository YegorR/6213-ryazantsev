package ru.cft.focusstart.ryazantsev.logic;

import org.junit.Assert;
import org.junit.Test;


public class FieldCreatorTest {
    @Test
    public void testSmallField() {
        IntCouple size = new IntCouple(9, 9);
        IntCouple initialCell = new IntCouple(5, 5);
        int expectedMines = 10;
        testField(size, initialCell, expectedMines);
    }

    @Test
    public void testAverageField() {
        IntCouple size = new IntCouple(15, 20);
        IntCouple initialCell = new IntCouple(10, 10);
        int expectedMines = 50;
        testField(size, initialCell, expectedMines);
    }

    @Test
    public void testBigField() {
        IntCouple size = new IntCouple(20, 30);
        IntCouple initialCell = new IntCouple(10, 15);
        int expectedMines = 75;
        testField(size, initialCell, expectedMines);
    }

    static private void testField(IntCouple size, IntCouple initialCell, int expectedMines) {
        FieldCreator fieldCreator = new FieldCreator(size, initialCell, expectedMines);
        int[][] field = fieldCreator.getField();

        int realMines = 0;
        for (int i = 0; i < size.getX(); ++i) {
            for (int j = 0; j < size.getY(); ++j) {
                int number = field[i][j];
                if ((number != CellValues.ZERO) && (i == initialCell.getX()) && (j == initialCell.getY())) {
                    Assert.fail("На первой нажатой ячейке не ноль!");
                }
                if (number == CellValues.MINE) {
                    realMines++;
                } else if ((number >= CellValues.ZERO) && (number <= CellValues.EIGHT)) {
                    int realMinesAround = countMinesAround(field, i, j);
                    Assert.assertEquals("Неверная цифра в поле!", (long) field[i][j], realMinesAround);
                }
            }
        }
        Assert.assertEquals("Неверное количество мин!", expectedMines, realMines);
    }

    static private int countMinesAround(int[][] field, int row, int column) {
        int result = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if ((i == 0) && (j == 0)) {
                    continue;
                }
                try {
                    if (field[row + i][column + j] == CellValues.MINE) {
                        result++;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        return result;
    }
}