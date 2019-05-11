package ru.cft.focusstart.ryazantsev.logic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
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
    private Map<IntCouple, ViewCellValue> expectedCells;

    @Before
    public void initViewField() {
        fieldView = mock(FieldView.class);
        fieldLogic = new FieldLogic(field, fieldView);
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

        fieldLogic.pressCell(new IntCouple(0, 7), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
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

        fieldLogic.pressCell(new IntCouple(4, 5), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testOneCell() {
        expectedCells.put(new IntCouple(1, 2), ViewCellValue.ONE);

        fieldLogic.pressCell(new IntCouple(1, 2), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testThreeCell() {
        expectedCells.put(new IntCouple(3, 4), ViewCellValue.THREE);

        fieldLogic.pressCell(new IntCouple(3, 4), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
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

        fieldLogic.pressCell(new IntCouple(2, 4), true);

        verify(fieldView).updateCells(expectedCells, GameStatus.DEFEAT);
    }

    @Test
    public void testVictory() {
        InOrder inOrder = inOrder(fieldView);
        int[][] smallField = {
                {MINE, ONE, ZERO},
                {TWO, TWO, ONE},
                {ONE, MINE, ONE}
        };
        fieldLogic = new FieldLogic(smallField, fieldView);
        expectedCells.put(new IntCouple(0, 2), ViewCellValue.ZERO);
        expectedCells.put(new IntCouple(0, 1), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(1, 1), ViewCellValue.TWO);
        expectedCells.put(new IntCouple(1, 2), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(0, 2), true);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 2), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(2, 2), true);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 1), ViewCellValue.FLAG);
        fieldLogic.pressCell(new IntCouple(2, 1), false);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(2, 0), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(2, 0), true);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(1, 0), ViewCellValue.TWO);
        fieldLogic.pressCell(new IntCouple(1, 0), true);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 0), ViewCellValue.FLAG);
        fieldLogic.pressCell(new IntCouple(0, 0), false);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.VICTORY);
    }

    @Test
    public void testTurnFlagOnOff() {
        InOrder inOrder = inOrder(fieldView);
        IntCouple cell = new IntCouple(0, 0);
        expectedCells.put(cell, ViewCellValue.FLAG);
        fieldLogic.pressCell(cell, false);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        fieldLogic.pressCell(cell, true);
        inOrder.verify(fieldView, times(0)).updateCells(any(), any());

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 0), ViewCellValue.UNTOUCHED);
        fieldLogic.pressCell(new IntCouple(0, 0), false);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testDefeatWithFlags() {
        InOrder inOrder = inOrder(fieldView);
        expectedCells.put(new IntCouple(0, 0), ViewCellValue.FLAG);
        fieldLogic.pressCell(new IntCouple(0, 0), false);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 1), ViewCellValue.FLAG);
        fieldLogic.pressCell(new IntCouple(0, 1), false);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 0), ViewCellValue.NOMINE);
        expectedCells.put(new IntCouple(2, 4), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 6), ViewCellValue.MINE);

        fieldLogic.pressCell(new IntCouple(2, 4), true);

        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.DEFEAT);
    }

    @Test
    public void testClickAround_1() {
        expectedCells.put(new IntCouple(7, 6), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(7, 6), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(6, 6), ViewCellValue.FLAG);
        fieldLogic.pressCell(new IntCouple(6, 6), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(7, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(6, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(6, 7), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(7, 7), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(7, 6), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testClickAround_2() {
        expectedCells.put(new IntCouple(0, 2), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(0, 2), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 1), ViewCellValue.FLAG);
        fieldLogic.pressCell(new IntCouple(0, 1), false);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(1, 1), ViewCellValue.ONE);
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
        fieldLogic.pressCell(new IntCouple(0, 2), true);
        verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);
    }

    @Test
    public void testClickAroundNothing() {
        InOrder inOrder = inOrder(fieldView);
        expectedCells.put(new IntCouple(0, 2), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(0, 2), true);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        fieldLogic.pressCell(new IntCouple(0, 2), true);
        inOrder.verify(fieldView, times(0)).updateCells(any(), any());
    }

    @Test
    public void testClickAroundWrong() {
        InOrder inOrder = inOrder(fieldView);
        expectedCells.put(new IntCouple(7, 6), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(7, 6), true);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(7, 5), ViewCellValue.FLAG);
        fieldLogic.pressCell(new IntCouple(7, 5), false);
        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.CONTINUED);

        expectedCells.clear();
        expectedCells.put(new IntCouple(0, 1), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(2, 4), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(3, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(4, 7), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 3), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(6, 6), ViewCellValue.MINE);
        expectedCells.put(new IntCouple(7, 5), ViewCellValue.NOMINE);
        expectedCells.put(new IntCouple(6, 5), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(6, 7), ViewCellValue.ONE);
        expectedCells.put(new IntCouple(7, 7), ViewCellValue.ONE);
        fieldLogic.pressCell(new IntCouple(7, 6), true);

        inOrder.verify(fieldView).updateCells(expectedCells, GameStatus.DEFEAT);
    }
}