package ru.cft.focusstart.ryazantsev.logic;


public enum ViewCellValue {
    UNTOUCHED, ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, FLAG, MINE, NOMINE;

    public static ViewCellValue getValueOfNumber(int numberValue) {
        switch (numberValue) {
            case CellValues.ZERO:
                return ZERO;
            case CellValues.ONE:
                return ONE;
            case CellValues.TWO:
                return TWO;
            case CellValues.THREE:
                return THREE;
            case CellValues.FOUR:
                return FOUR;
            case CellValues.FIVE:
                return FIVE;
            case CellValues.SIX:
                return SIX;
            case CellValues.SEVEN:
                return SEVEN;
            case CellValues.EIGHT:
                return EIGHT;
            default:
                return null;
        }
    }
}
