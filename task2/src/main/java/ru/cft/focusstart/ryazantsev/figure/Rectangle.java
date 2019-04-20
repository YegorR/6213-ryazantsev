package ru.cft.focusstart.ryazantsev.figure;

import ru.cft.focusstart.ryazantsev.exception.WrongParametersException;

public class Rectangle extends FigureImpl implements Figure {
    private final static String NAME = "Прямоугольник";

    private double width;
    private double height;

    public Rectangle(double a, double b) throws WrongParametersException {
        if ((a <= 0) || (b <= 0)) {
            throw new WrongParametersException();
        }

        if (a >= b) {
            width = a;
            height = b;
        } else {
            width = b;
            height = a;
        }

        createDescription();
    }

    private void createDescription() {
        addToDescription("Тип фигуры", NAME);
        addToDescription("Площадь", calculateSquare());
        addToDescription("Периметр", calculatePerimeter());
        addToDescription("Диагональ", calculateDiagonal());
        addToDescription("Ширина", width);
        addToDescription("Длина", height);
    }

    private double calculateSquare() {
        return width * height;
    }

    private double calculatePerimeter() {
        return (width + height) * 2;
    }

    private double calculateDiagonal() {
        return Math.sqrt(width * width + height * height);
    }
}
