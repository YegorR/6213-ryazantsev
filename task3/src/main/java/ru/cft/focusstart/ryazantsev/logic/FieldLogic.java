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
    private ViewCellValue[][] statusField;
    private int minesCount;

    public FieldLogic(int[][] field, int minesCount) {
        this.field = field;
        this.minesCount = minesCount;

        statusField = new ViewCellValue[field.length][field[0].length];
        for (ViewCellValue[] row : statusField) {
            Arrays.fill(row, ViewCellValue.UNTOUCHED);
        }
    }

    public FieldAnswer pressCell(IntCouple cell, boolean left) {
        int x = cell.getX();
        int y = cell.getY();
        Map<IntCouple, ViewCellValue> sentCells = new HashMap<>();
        boolean defeat = false;

        if (left) {
            if (statusField[x][y] == ViewCellValue.UNTOUCHED) {
                if ((field[x][y] >= ONE) && (field[x][y] <= EIGHT)) {
                    sentCells.put(cell, ViewCellValue.getValueOfNumber(field[x][y]));
                    statusField[x][y] = ViewCellValue.getValueOfNumber(field[x][y]);
                } else if (field[x][y] == MINE) {
                    defeat = true;
                    touchMineCell(sentCells);
                } else if (field[x][y] == ZERO) {
                    touchZeroCell(x, y, sentCells);
                }
            } else if (statusField[x][y] != ViewCellValue.FLAG) {
                defeat = touchTouchedCell(x, y, sentCells);
            }
        } else {
            if (statusField[x][y] == ViewCellValue.UNTOUCHED) {
                statusField[x][y] = ViewCellValue.FLAG;
                sentCells.put(cell, ViewCellValue.FLAG);
                minesCount--;
            } else if (statusField[x][y] == ViewCellValue.FLAG) {
                statusField[x][y] = ViewCellValue.UNTOUCHED;
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
        statusField[x][y] = ViewCellValue.getValueOfNumber(field[x][y]);
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
                if ((field[i][j] == MINE) && (statusField[i][j] == ViewCellValue.UNTOUCHED)) {
                    sentCells.put(new IntCouple(i, j), ViewCellValue.MINE);
                } else if ((field[i][j] != MINE) && (statusField[i][j] == ViewCellValue.FLAG)) {
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
                    if (statusField[x + i][y + j] == ViewCellValue.FLAG) {
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
                    if (statusField[x + i][y + j] != ViewCellValue.UNTOUCHED) {
                        continue;
                    }
                    if (field[x + i][y + j] == ZERO) {
                        touchZeroCell(x + i, y + j, sentCells);
                    } else if ((field[x + i][y + j] >= ONE) && (field[x + i][y + j] <= EIGHT)) {
                        sentCells.put(new IntCouple(x + i, y + j),
                                ViewCellValue.getValueOfNumber(field[x + i][y + j]));
                        statusField[x + i][y + j] = ViewCellValue.getValueOfNumber(field[x + i][y + j]);
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
                if (statusField[i][j] == ViewCellValue.UNTOUCHED) {
                    return false;
                }
                if ((statusField[i][j] == ViewCellValue.FLAG) && (field[i][j] != MINE)) {
                    return false;
                }
            }
        }
        return true;
    }
}