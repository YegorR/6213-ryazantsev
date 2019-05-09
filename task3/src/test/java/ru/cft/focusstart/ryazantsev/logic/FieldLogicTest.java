package ru.cft.focusstart.ryazantsev.logic;

import org.junit.Before;
import org.junit.Test;
import ru.cft.focusstart.ryazantsev.view.FieldView;

import java.util.HashMap;
import java.util.Map;


import static org.mockito.Mockito.*;
import static ru.cft.focusstart.ryazantsev.logic.CellValues.*;

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
    private FieldView fieldView;
    private Map<IntCouple, ViewCellValues> expectedCells;

    @Before
    public void initViewField() {
        fieldView = mock(FieldView.class);
        fieldLogic = new FieldLogic(field, fieldView);
        expectedCells = new HashMap<>();
    }

    @Test
    public void testZeroCell_1() {
        expectedCells.put(new IntCouple(0, 2), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 2), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 3), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 4), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(2, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(2, 6), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(2, 7), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(2, 8), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(0, 3), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 4), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 5), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 6), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 7), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 8), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(1, 6), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(1, 7), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(1, 8), ViewCellValues.ZERO);

        fieldLogic.pressCell(new IntCouple(0, 7), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testZeroCell_2() {
        expectedCells.put(new IntCouple(4, 5), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(3, 4), ViewCellValues.THREE);
        expectedCells.put(new IntCouple(3, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(3, 6), ViewCellValues.TWO);
        expectedCells.put(new IntCouple(4, 6), ViewCellValues.TWO);
        expectedCells.put(new IntCouple(5, 6), ViewCellValues.TWO);
        expectedCells.put(new IntCouple(5, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(5, 4), ViewCellValues.TWO);
        expectedCells.put(new IntCouple(4, 4), ViewCellValues.TWO);

        fieldLogic.pressCell(new IntCouple(4, 5), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testOneCell() {
        expectedCells.put(new IntCouple(1, 2), ViewCellValues.ONE);

        fieldLogic.pressCell(new IntCouple(1, 2), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testThreeCell() {
        expectedCells.put(new IntCouple(3, 4), ViewCellValues.THREE);

        fieldLogic.pressCell(new IntCouple(3, 4), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testMine() {
        expectedCells.put(new IntCouple(0, 1), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(2, 4), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(3, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(3, 7), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(4, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(4, 7), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(6, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(6, 6), ViewCellValues.MINE);

        fieldLogic.pressCell(new IntCouple(2, 4), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.DEFEAT);
    }

    @Test
    public void testVictory() {
        int[][] smallField = {
                {MINE, ONE, ZERO},
                {TWO, TWO, ONE},
                {ONE, MINE, ONE}
        };
        fieldLogic = new FieldLogic(smallField, fieldView);
        expectedCells.put(new IntCouple(2, 0), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(1, 0), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 1), ViewCellValues.TWO);
        expectedCells.put(new IntCouple(1, 2), ViewCellValues.ONE);
        fieldLogic.pressCell(new IntCouple(2, 0), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 2), ViewCellValues.ONE);
        fieldLogic.pressCell(new IntCouple(2, 2), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 1), ViewCellValues.FLAG);
        fieldLogic.pressCell(new IntCouple(2, 1), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 0), ViewCellValues.ONE);
        fieldLogic.pressCell(new IntCouple(2, 0), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(1, 0), ViewCellValues.TWO);
        fieldLogic.pressCell(new IntCouple(1, 0), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 0), ViewCellValues.FLAG);
        fieldLogic.pressCell(new IntCouple(0, 0), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.VICTORY);
    }

    @Test
    public void testTurnFlagOnOff() {
        IntCouple cell = new IntCouple(0, 0);
        expectedCells.put(cell, ViewCellValues.FLAG);
        fieldLogic.pressCell(cell, false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        fieldLogic.pressCell(cell, true);
        verify(fieldView, times(0)).updateCells(any(), any());

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 0), ViewCellValues.UNTOUCHED);
        fieldLogic.pressCell(new IntCouple(0, 0), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testDefeatWithFlags() {
        expectedCells.put(new IntCouple(0, 0), ViewCellValues.FLAG);
        fieldLogic.pressCell(new IntCouple(0, 0), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.put(new IntCouple(0, 1), ViewCellValues.FLAG);
        fieldLogic.pressCell(new IntCouple(0, 1), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 0), ViewCellValues.NOMINE);
        expectedCells.put(new IntCouple(2, 4), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(3, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(3, 7), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(4, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(4, 7), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(6, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(6, 6), ViewCellValues.MINE);

        fieldLogic.pressCell(new IntCouple(2, 4), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.DEFEAT);
    }

    @Test
    public void testClickAround_1() {
        expectedCells.put(new IntCouple(7, 6), ViewCellValues.ONE);
        fieldLogic.pressCell(new IntCouple(7, 6), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(6, 6), ViewCellValues.FLAG);
        fieldLogic.pressCell(new IntCouple(6, 6), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(7, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(6, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(6, 7), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(7, 7), ViewCellValues.ONE);
        fieldLogic.pressCell(new IntCouple(7, 6), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testClickAround_2() {
        expectedCells.put(new IntCouple(0, 2), ViewCellValues.ONE);
        fieldLogic.pressCell(new IntCouple(0, 2), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 1), ViewCellValues.FLAG);
        fieldLogic.pressCell(new IntCouple(0, 1), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(1, 1), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(0, 2), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 2), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 3), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 4), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(1, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(2, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(2, 6), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(2, 7), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(2, 8), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(0, 3), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 4), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 5), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 6), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 7), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(0, 8), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(1, 6), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(1, 7), ViewCellValues.ZERO);
        expectedCells.put(new IntCouple(1, 8), ViewCellValues.ZERO);
        fieldLogic.pressCell(new IntCouple(0, 2), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testClickAroundNothing() {
        expectedCells.put(new IntCouple(0, 2), ViewCellValues.ONE);
        fieldLogic.pressCell(new IntCouple(0, 2), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        fieldLogic.pressCell(new IntCouple(0, 2), true);
        verify(fieldView, times(0)).updateCells(any(), any());
    }

    @Test
    public void testClickAroundWrong() {
        expectedCells.put(new IntCouple(7, 6), ViewCellValues.ONE);
        fieldLogic.pressCell(new IntCouple(7, 6), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(7, 5), ViewCellValues.FLAG);
        fieldLogic.pressCell(new IntCouple(7, 5), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 1), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(2, 4), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(3, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(3, 7), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(4, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(4, 7), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(6, 3), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(6, 6), ViewCellValues.MINE);
        expectedCells.put(new IntCouple(7, 5), ViewCellValues.NOMINE);
        expectedCells.put(new IntCouple(6, 5), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(6, 7), ViewCellValues.ONE);
        expectedCells.put(new IntCouple(7, 7), ViewCellValues.ONE);
        verify(fieldView).updateCells(expectedCells, GameStatus.DEFEAT);
    }
}