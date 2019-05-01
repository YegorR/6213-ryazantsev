package ru.cft.focusstart.ryazantsev.figure;

import ru.cft.focusstart.ryazantsev.exception.WrongParametersException;

public class Triangle extends FigureImpl {
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
        addToDescription("Тип фигуры", getName());
        addToDescription("Площадь", calculateSquare());
        addToDescription("Периметр", calculatePerimeter());
        addToDescription("Сторона a", a);
        addToDescription("Угол напротив стороны a", calculateAngle(a, b, c));
        addToDescription("Сторона b", b);
        addToDescription("Угол напротив стороны b", calculateAngle(b, a, c));
        addToDescription("Сторона c", c);
        addToDescription("Угол напротив стороны c", calculateAngle(c, a, b));
    }

    private double calculateSquare() {
        double p = calculatePerimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    private double calculatePerimeter() {
        return a + b + c;
    }

    private static double calculateAngle(double oppositeSide, double leftSide, double rightSide) {
        return Math.toDegrees(Math.acos((leftSide * leftSide + rightSide * rightSide - oppositeSide * oppositeSide) /
                (2 * leftSide * rightSide)));
    }

    @Override
    public String getName() {
        return "Треугольник";
    }
}
