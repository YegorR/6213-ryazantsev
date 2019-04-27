package ru.cft.focusstart.ryazantsev;


import ru.cft.focusstart.ryazantsev.exception.FileException;
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

    public void read() throws FileException, WrongFigureNameException {
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
            throw new FileException("The input file is not found!");
        } catch (IOException ex) {
            throw new FileException("The IOException of the input file!");
        } catch (NoSuchElementException ex) {
            throw new FileException("The input file is empty!");
        }
    }

    public FigureName getFigureName() {
        return figureName;
    }

    public List<Double> getParameters() {
        return parameters;
    }
}
