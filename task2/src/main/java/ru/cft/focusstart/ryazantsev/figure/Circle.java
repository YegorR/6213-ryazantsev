package ru.cft.focusstart.ryazantsev.figure;

public class Circle extends FigureImpl implements Figure {
    private final static String NAME = "Круг";
    private double radius;

    public Circle(double radius) {
        this.radius = radius;

        createDescription();
    }

    private void createDescription() {
        addToDescription("Тип фигуры", NAME);
        addToDescription("Площадь", calculateSquare());
        addToDescription("Периметр", calculatePerimeter());
        addToDescription("Радиус", radius);
        addToDescription("Диаметр", calculateDiameter());
    }

    private double calculateSquare() {
        return Math.PI * radius * radius;
    }

    private double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    private double calculateDiameter() {
        return 2 * radius;
    }
}
