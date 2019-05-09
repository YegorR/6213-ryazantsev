//package ru.cft.focusstart.ryazantsev.logic;
//
//
//import ru.cft.focusstart.ryazantsev.view.FieldView;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Observable;
//import java.util.Set;
//
//import static ru.cft.focusstart.ryazantsev.logic.CellValues.*;
//
//
//public class FieldLogic extends Observable {
//    private int[][] field;
//    private int[][] statusField;
//    private FieldView fieldView;
//
//    public FieldLogic(int[][] field, FieldView fieldView, IntCouple initialCell) {
//        this.field = field;
//        this.fieldView = fieldView;
//
//        statusField = new int[field.length][field[0].length];
//        for (int[] row : statusField) {
//            Arrays.fill(row, CLOSED);
//        }
//        if (initialCell != null) {
//            press(initialCell, false);
//        }
//    }
//
//    public void press(IntCouple cell, boolean flag) {
//        int x = cell.getX();
//        int y = cell.getY();
//
//        if (flag) {
//            if (statusField[x][y] == CLOSED) {
//                statusField[x][y] = FLAG;
//            } else if (statusField[x][y] == FLAG) {
//                statusField[x][y] = CLOSED;
//            } else {
//                return;
//            }
//        } else {
//            if (field[x][y] == MINE) {
//                sendFail();
//                return;
//            } else if (field[x][y] == ZERO) {
//                sendVoid(x, y);
//            } else {
//                fieldView.updateCell(cell, field[x][y]);
//                statusField[x][y] = OPENED;
//            }
//        }
//        if (isVictory()) {
//            fieldView.updateVictory();
//        }
//    }
//
//    private boolean isVictory() {
//        for (int i = 0; i < statusField.length; ++i) {
//            for (int j = 0; j < statusField[0].length; ++j) {
//                if (statusField[i][j] == CLOSED) {
//                    return false;
//                }
//                if ((statusField[i][j] == FLAG) && (field[i][j] != MINE)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    private void sendFail() {
//        for (int i = 0; i < field.length; ++i) {
//            for (int j = 0; j < field[0].length; ++j) {
//                if (field[i][j] == MINE) {
//                    fieldView.updateCell(new IntCouple(i, j), MINE);
//                }
//            }
//        }
//        fieldView.updateLose();
//    }
//
//    private void sendVoid(int x, int y) {
//        Set<IntCouple> sentCells = new HashSet<>();
//        verifyVoidCell(x, y, sentCells);
//        for (IntCouple cell : sentCells) {
//            fieldView.updateCell(cell, field[cell.getX()][cell.getY()]);
//            statusField[cell.getX()][cell.getY()] = OPENED;
//        }
//    }
//
//    private void verifyVoidCell(int x, int y, Set<IntCouple> sentCells) {
//        if ((x < 0) || (y < 0) || (x > field.length - 1) || (y > field[0].length - 1)) {
//            return;
//        }
//        if (statusField[x][y] == OPENED) {
//            return;
//        }
//        sentCells.add(new IntCouple(x, y));
//        if (field[x][y] != ZERO) {
//            return;
//        }
//        for (int i = -1; i <= 1; ++i) {
//            for (int j = -1; j <= 1; ++j) {
//                if ((i == 0) && (j == 0)) {
//                    continue;
//                }
//                if (!sentCells.contains(new IntCouple(x + i, y + j))) {
//                    verifyVoidCell(x + i, y + j, sentCells);
//                }
//            }
//        }
//    }
//}

package ru.cft.focusstart.ryazantsev.logic;

import ru.cft.focusstart.ryazantsev.view.FieldView;

public class FieldLogic {
    public FieldLogic(int[][] field, FieldView FieldView) {

    }

    public void pressCell(IntCouple cell, boolean left) {

    }
}