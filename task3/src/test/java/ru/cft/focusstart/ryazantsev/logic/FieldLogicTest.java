package ru.cft.focusstart.ryazantsev.logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.cft.focusstart.ryazantsev.util.FieldAnswer;
import ru.cft.focusstart.ryazantsev.util.GameStatus;
import ru.cft.focusstart.ryazantsev.util.IntCouple;
import ru.cft.focusstart.ryazantsev.util.ViewCellValue;

import java.util.HashMap;
import java.util.Map;

import static ru.cft.focusstart.ryazantsev.util.CellValues.*;

public class FieldLogicTest {
    private int[][] field = {
            {ONE, MINE, ONE, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO},
            {ONE, ONE, ONE, ONE, ONE, ONE, ZERO, ZERO, ZERO},
            {ZERO, ZERO, ONE, TWO, MINE, ONE, ONE, ONE, ONE},
            {ZERO, ZERO, TWO, MINE, THREE, ONE, TWO, MINE, TWO},
            {ZERO, ZERO, TWO, MINE, TWO, ZERO, TWO, MINE, TWO},
            {ZERO, ZERO, TWO, TWO, TWO, ONE, TWO, TWO, ONE},
            {ZERO, ZERO, ONE, MINE, ONE, ONE, MINE, ONE, ZERO},
            {ZERO, ZERO, ONE, ONE, ONE, ONE, ONE, ONE, ZERO}
    };
    private FieldLogic fieldLogic;
    private Map<IntCouple, ViewCellValue> expectedCells;
    private static final int minesCount = 8;

    @Before
    public void initViewField() {
        fieldLogic = new FieldLogic(field, minesCount);
        expectedCells = new HashMap<>();
    }

    @Test
    public void testZeroCell_1() {
        expectedCells.put(new IntCouple(0, 2), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(1, 2), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(1, 3), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(1, 4), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(1, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(2, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(2, 6), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(2, 7), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(2, 8), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(0, 3), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(0, 4), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(0, 5), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(0, 6), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(0, 7), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(0, 8), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(1, 6), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(1, 7), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(1, 8), ViewCellValue.ZERO);

        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(0, 7), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testZeroCell_2() {
        expectedCells.put(new IntCouple(4, 5), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(3, 4), ViewCellValue.THREE);
        expectedCells.put(new IntCouple(3, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(3, 6), ViewCellValue.TWO);
        expectedCells.put(new IntCouple(4, 6), ViewCellValue.TWO);
        expectedCells.put(new IntCouple(5, 6), ViewCellValue.TWO);
        expectedCells.put(new IntCouple(5, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(5, 4), ViewCellValue.TWO);
        expectedCells.put(new IntCouple(4, 4), ViewCellValue.TWO);

        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(4, 5), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testOneCell() {
        expectedCells.put(new IntCouple(1, 2), ViewCellValue.ONE);

        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(1, 2), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testThreeCell() {
        expectedCells.put(new IntCouple(3, 4), ViewCellValue.THREE);

        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(3, 4), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testMine() {
        expectedCells.put(new IntCouple(0, 1), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(2, 4), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 6), ViewCellValue.MINE);

        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(2, 4), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.DEFEAT, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testVictory() {
        int[][] smallField = {
                {MINE, ONE, ZERO},
                {TWO, TWO, ONE},
                {ONE, MINE, ONE}
        };
        int minesCount = 2;
        fieldLogic = new FieldLogic(smallField, minesCount);
        expectedCells.put(new IntCouple(0, 2), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(0, 1), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(1, 1), ViewCellValue.TWO);
        expectedCells.put(new IntCouple(1, 2), ViewCellValue.ONE);

        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(0, 2), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 2), ViewCellValue.ONE);

        actualAnswer = fieldLogic.pressCell(new IntCouple(2, 2), true);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 1), ViewCellValue.FLAG);

        actualAnswer = fieldLogic.pressCell(new IntCouple(2, 1), false);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 0), ViewCellValue.ONE);
        actualAnswer = fieldLogic.pressCell(new IntCouple(2, 0), true);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(1, 0), ViewCellValue.TWO);
        actualAnswer = fieldLogic.pressCell(new IntCouple(1, 0), true);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 0), ViewCellValue.FLAG);
        actualAnswer = fieldLogic.pressCell(new IntCouple(0, 0), false);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.VICTORY, minesCount - 2);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testTurnFlagOnOff() {
        IntCouple cell = new IntCouple(0, 0);
        expectedCells.put(cell, ViewCellValue.FLAG);
        FieldAnswer actualAnswer = fieldLogic.pressCell(cell, false);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        actualAnswer = fieldLogic.pressCell(cell, true);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.put(new IntCouple(0, 0), ViewCellValue.UNTOUCHED);
        actualAnswer = fieldLogic.pressCell(cell, false);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testDefeatWithFlags() {
        expectedCells.put(new IntCouple(0, 0), ViewCellValue.FLAG);
        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(0, 0), false);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 1), ViewCellValue.FLAG);
        actualAnswer = fieldLogic.pressCell(new IntCouple(0, 1), false);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 2);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 0), ViewCellValue.NO_MINE);
        expectedCells.put(new IntCouple(2, 4), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 6), ViewCellValue.MINE);

        actualAnswer = fieldLogic.pressCell(new IntCouple(2, 4), true);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.DEFEAT, minesCount - 2);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testClickAround() {
        expectedCells.put(new IntCouple(7, 6), ViewCellValue.ONE);
        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(7, 6), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(6, 6), ViewCellValue.FLAG);
        actualAnswer = fieldLogic.pressCell(new IntCouple(6, 6), false);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(7, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(6, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(6, 7), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(7, 7), ViewCellValue.ONE);
        actualAnswer = fieldLogic.pressCell(new IntCouple(7, 6), true);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testClickAroundNothing() {
        expectedCells.put(new IntCouple(0, 2), ViewCellValue.ONE);
        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(0, 2), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        fieldLogic.pressCell(new IntCouple(0, 2), true);
        actualAnswer = fieldLogic.pressCell(new IntCouple(0, 2), true);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void testClickAroundWrong() {
        expectedCells.put(new IntCouple(7, 6), ViewCellValue.ONE);
        FieldAnswer actualAnswer = fieldLogic.pressCell(new IntCouple(7, 6), true);
        FieldAnswer expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(7, 5), ViewCellValue.FLAG);
        actualAnswer = fieldLogic.pressCell(new IntCouple(7, 5), false);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.CONTINUED, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 1), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(2, 4), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 6), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(7, 5), ViewCellValue.NO_MINE);
        expectedCells.put(new IntCouple(6, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(6, 7), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(7, 7), ViewCellValue.ONE);

        actualAnswer = fieldLogic.pressCell(new IntCouple(7, 6), true);
        expectedAnswer = new FieldAnswer(expectedCells, GameStatus.DEFEAT, minesCount - 1);
        Assert.assertEquals(expectedAnswer, actualAnswer);
    }
}