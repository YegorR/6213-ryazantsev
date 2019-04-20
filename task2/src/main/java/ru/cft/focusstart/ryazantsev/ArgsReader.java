package ru.cft.focusstart.ryazantsev;


import ru.cft.focusstart.ryazantsev.exception.EmptyInputFileException;
import ru.cft.focusstart.ryazantsev.exception.IOInputException;
import ru.cft.focusstart.ryazantsev.exception.InputFileNotFoundException;
import ru.cft.focusstart.ryazantsev.exception.WrongFigureNameException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ArgsReader {
    private String filename;
    private FigureName figureName = null;
    private List<Double> parameters = new ArrayList<>();

    public ArgsReader(String filename) {
        this.filename = filename;
    }

    public void read() throws InputFileNotFoundException, EmptyInputFileException, IOInputException, WrongFigureNameException {
        try (FileReader fr = new FileReader(filename)) {
            Scanner scanner = new Scanner(fr);
            String line = scanner.nextLine();
            line = line.trim().toUpperCase();
            switch (line) {
                case "CIRCLE":
                    figureName = FigureName.CIRCLE;
                    break;
                case "RECTANGLE":
                    figureName = FigureName.RECTANGLE;
                    break;
                case "TRIANGLE":
                    figureName = FigureName.TRIANGLE;
                    break;
                default:
                    throw new WrongFigureNameException();
            }
            while (scanner.hasNextDouble()) {
                parameters.add(scanner.nextDouble());
            }
        } catch (FileNotFoundException ex) {
            throw new InputFileNotFoundException();
        } catch (IOException ex) {
            throw new IOInputException();
        } catch (NoSuchElementException ex) {
            throw new EmptyInputFileException();
        }
    }

    public FigureName getFigureName() {
        return figureName;
    }

    public List<Double> getParameters() {
        return parameters;
    }
}
