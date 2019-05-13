//package ru.cft.focusstart.ryazantsev.logic;
//
//
//import ru.cft.focusstart.ryazantsev.view.Field;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Observable;
//import java.util.Set;
//
//import static ru.cft.focusstart.ryazantsev.util.CellValues.*;
//
//
//public class FieldLogic extends Observable {
//    private int[][] field;
//    private int[][] statusField;
//    private Field fieldPanel;
//
//    public FieldLogic(int[][] field, Field fieldPanel, IntCouple initialCell) {
//        this.field = field;
//        this.fieldPanel = fieldPanel;
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
//                fieldPanel.updateCell(cell, field[x][y]);
//                statusField[x][y] = OPENED;
//            }
//        }
//        if (isVictory()) {
//            fieldPanel.updateVictory();
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
//                    fieldPanel.updateCell(new IntCouple(i, j), MINE);
//                }
//            }
//        }
//        fieldPanel.updateLose();
//    }
//
//    private void sendVoid(int x, int y) {
//        Set<IntCouple> sentCells = new HashSet<>();
//        verifyVoidCell(x, y, sentCells);
//        for (IntCouple cell : sentCells) {
//            fieldPanel.updateCell(cell, field[cell.getX()][cell.getY()]);
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

import ru.cft.focusstart.ryazantsev.util.FieldAnswer;
import ru.cft.focusstart.ryazantsev.util.GameStatus;
import ru.cft.focusstart.ryazantsev.util.IntCouple;
import ru.cft.focusstart.ryazantsev.util.ViewCellValue;

import static ru.cft.focusstart.ryazantsev.util.CellValues.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FieldLogic {
    private int[][] field;
    private StatusCellValue[][] statusField;
    private int minesCount;

    public FieldLogic(int[][] field, int minesCount) {
        this.field = field;
        this.minesCount = minesCount;

        statusField = new StatusCellValue[field.length][field[0].length];
        for (StatusCellValue[] row : statusField) {
            Arrays.fill(row, StatusCellValue.UNTOUCHED);
        }
    }

    public FieldAnswer pressCell(IntCouple cell, boolean left) {
        int x = cell.getX();
        int y = cell.getY();
        Map<IntCouple, ViewCellValue> sentCells = new HashMap<>();
        boolean defeat = false;

        if (left) {
            if (statusField[x][y] == StatusCellValue.UNTOUCHED) {
                if ((field[x][y] >= ONE) && (field[x][y] <= EIGHT)) {
                    sentCells.put(cell, ViewCellValue.getValueOfNumber(field[x][y]));
                    statusField[x][y] = StatusCellValue.TOUCHED;
                } else if (field[x][y] == MINE) {
                    defeat = true;
                    touchMineCell(sentCells);
                } else if (field[x][y] == ZERO) {
                    touchZeroCell(x, y, sentCells);
                }
            } else if (statusField[x][y] == StatusCellValue.TOUCHED) {
                defeat = touchTouchedCell(x, y, sentCells);
            }
        } else {
            if (statusField[x][y] == StatusCellValue.UNTOUCHED) {
                statusField[x][y] = StatusCellValue.FLAG;
                sentCells.put(cell, ViewCellValue.FLAG);
                minesCount--;
            } else if (statusField[x][y] == StatusCellValue.FLAG) {
                statusField[x][y] = StatusCellValue.UNTOUCHED;
                sentCells.put(cell, ViewCellValue.UNTOUCHED);
                minesCount++;
            }
        }

        if (defeat) {
            return new FieldAnswer(sentCells, GameStatus.DEFEAT, minesCount);
        } else if (isVictory()) {
            return new FieldAnswer(sentCells, GameStatus.VICTORY, minesCount);
        } else {
            return new FieldAnswer(sentCells, GameStatus.CONTINUED, minesCount);
        }
    }


    private void touchZeroCell(int x, int y, Map<IntCouple, ViewCellValue> sentCells) {
        if ((x < 0) || (y < 0) || (x > field.length - 1) || (y > field[0].length - 1)) {
            return;
        }
        sentCells.put(new IntCouple(x, y), ViewCellValue.getValueOfNumber(field[x][y]));
        statusField[x][y] = StatusCellValue.TOUCHED;
        if (field[x][y] != ZERO) {
            return;
        }
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if ((i == 0) && (j == 0)) {
                    continue;
                }
                if (!sentCells.keySet().contains(new IntCouple(x + i, y + j))) {
                    touchZeroCell(x + i, y + j, sentCells);
                }
            }
        }
    }

    private void touchMineCell(Map<IntCouple, ViewCellValue> sentCells) {
        for (int i = 0; i < statusField.length; ++i) {
            for (int j = 0; j < statusField[0].length; ++j) {
                if ((field[i][j] == MINE) && (statusField[i][j] == StatusCellValue.UNTOUCHED)) {
                    sentCells.put(new IntCouple(i, j), ViewCellValue.MINE);
                } else if ((field[i][j] != MINE) && (statusField[i][j] == StatusCellValue.FLAG)) {
                    sentCells.put(new IntCouple(i, j), ViewCellValue.NO_MINE);
                }
            }
        }
    }

    private boolean touchTouchedCell(int x, int y, Map<IntCouple, ViewCellValue> sentCells) {
        if (field[x][y] == ZERO) {
            return false;
        }

        int flagsAround = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if ((i == 0) && (j == 0)) {
                    continue;
                }
                try {
                    if (statusField[x + i][y + j] == StatusCellValue.FLAG) {
                        flagsAround++;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        if (flagsAround != field[x][y]) {
            return false;
        }

        boolean defeat = false;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if ((i == 0) && (j == 0)) {
                    continue;
                }
                try {
                    if (statusField[x + i][y + j] != StatusCellValue.UNTOUCHED) {
                        continue;
                    }
                    if (field[x + i][y + j] == ZERO) {
                        touchZeroCell(x + i, y + j, sentCells);
                    } else if ((field[x + i][y + j] >= ONE) && (field[x + i][y + j] <= EIGHT)) {
                        sentCells.put(new IntCouple(x + i, y + j),
                                ViewCellValue.getValueOfNumber(field[x + i][y + j]));
                        statusField[x + i][y + j] = StatusCellValue.TOUCHED;
                    } else if (field[x + i][y + j] == MINE) {
                        if (!defeat) {
                            touchMineCell(sentCells);
                            defeat = true;
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        return defeat;
    }

    private boolean isVictory() {
        for (int i = 0; i < statusField.length; ++i) {
            for (int j = 0; j < statusField[0].length; ++j) {
                if (statusField[i][j] == StatusCellValue.UNTOUCHED) {
                    return false;
                }
                if ((statusField[i][j] == StatusCellValue.FLAG) && (field[i][j] != MINE)) {
                    return false;
                }
            }
        }
        return true;
    }

    private enum StatusCellValue {
        UNTOUCHED, TOUCHED, FLAG
    }
}