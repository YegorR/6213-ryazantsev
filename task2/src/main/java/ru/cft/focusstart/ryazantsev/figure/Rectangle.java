package ru.cft.focusstart.ryazantsev.figure;

import ru.cft.focusstart.ryazantsev.exception.WrongParametersException;

public class Rectangle extends FigureImpl {
    private double width;
    private double length;

    public Rectangle(double a, double b) throws WrongParametersException {
        if ((a <= 0) || (b <= 0)) {
            throw new WrongParametersException();
        }

        if (a >= b) {
            width = b;
            length = a;
        } else {
            width = a;
            length = b;
        }

        createDescription();
    }

    private void createDescription() {
        addToDescription("Тип фигуры", getName());
        addToDescription("Площадь", calculateSquare());
        addToDescription("Периметр", calculatePerimeter());
        addToDescription("Диагональ", calculateDiagonal());
        addToDescription("Ширина", width);
        addToDescription("Длина", length);
    }

    private double calculateSquare() {
        return width * length;
    }

    private double calculatePerimeter() {
        return (width + length) * 2;
    }

    private double calculateDiagonal() {
        return Math.sqrt(width * width + length * length);
    }

    @Override
    public String getName() {
        return "Прямоугольник";
    }
}
