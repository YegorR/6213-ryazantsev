package ru.cft.focusstart.ryazantsev.figure;

import ru.cft.focusstart.ryazantsev.exception.WrongParametersException;

public class Triangle extends FigureImpl implements Figure {
    private final static String NAME = "Треугольник";
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) throws WrongParametersException {
        if ((a <= 0) || (b <= 0) || (c <= 0)) {
            throw new WrongParametersException();
        }
        if ((a + b <= c) || (a + c <= b) || (b + c <= a)) {
            throw new WrongParametersException();
        }

        this.a = a;
        this.b = b;
        this.c = c;

        createDescription();
    }

    private void createDescription() {
        addToDescription("Тип фигуры", NAME);
        addToDescription("Площадь", calculateSquare());
        addToDescription("Периметр", calculatePerimeter());
        addToDescription("Сторона a", a);
        addToDescription("Угол напротив стороны a", calculateAngleA());
        addToDescription("Сторона b", b);
        addToDescription("Угол напротив стороны b", calculateAngleB());
        addToDescription("Сторона c", c);
        addToDescription("Угол напротив стороны c", calculateAngleC());
    }

    private double calculateSquare() {
        double p = calculatePerimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    private double calculatePerimeter() {
        return a + b + c;
    }

    private double calculateAngleA() {
        double angle = Math.acos((b * b + c * c - a * a) / (2 * b * c));
        return Math.toDegrees(angle);
    }

    private double calculateAngleB() {
        double angle = Math.acos((a * a + c * c - b * b) / (2 * a * c));
        return Math.toDegrees(angle);
    }

    private double calculateAngleC() {
        double angle = Math.acos((a * a + b * b - c * c) / (2 * a * b));
        return Math.toDegrees(angle);
    }
}
