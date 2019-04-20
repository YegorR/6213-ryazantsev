package ru.cft.focusstart.ryazantsev;

import ru.cft.focusstart.ryazantsev.exception.NotEnoughParametersException;
import ru.cft.focusstart.ryazantsev.exception.WrongFigureNameException;
import ru.cft.focusstart.ryazantsev.exception.WrongParametersException;
import ru.cft.focusstart.ryazantsev.figure.Circle;
import ru.cft.focusstart.ryazantsev.figure.Figure;
import ru.cft.focusstart.ryazantsev.figure.Rectangle;
import ru.cft.focusstart.ryazantsev.figure.Triangle;

import java.util.List;

public class FigureCreator {
    public static Figure createFigure(FigureName figureName, List<Double> parameters) throws WrongParametersException, NotEnoughParametersException, WrongFigureNameException {
        switch (figureName) {
            case CIRCLE:
                if (parameters.size() < 1) {
                    throw new NotEnoughParametersException();
                }
                return new Circle(parameters.get(0));
            case RECTANGLE:
                if (parameters.size() < 2) {
                    throw new NotEnoughParametersException();
                }
                return new Rectangle(parameters.get(0), parameters.get(1));
            case TRIANGLE:
                if (parameters.size() < 3) {
                    throw new NotEnoughParametersException();
                }
                return new Triangle(parameters.get(0), parameters.get(1), parameters.get(2));
            default:
                throw new WrongFigureNameException();
        }
    }
}
