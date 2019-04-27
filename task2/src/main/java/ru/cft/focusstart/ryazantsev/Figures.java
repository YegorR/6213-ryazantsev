package ru.cft.focusstart.ryazantsev;

import ru.cft.focusstart.ryazantsev.exception.*;
import ru.cft.focusstart.ryazantsev.figure.Figure;
import ru.cft.focusstart.ryazantsev.filework.ArgsReader;
import ru.cft.focusstart.ryazantsev.filework.DescriptionWriter;


public class Figures {
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                throw new NoArgsException();
            }

            ArgsReader argsReader = new ArgsReader(args[0]);
            argsReader.read();
            Figure figure = FigureCreator.createFigure(argsReader.getFigureName(), argsReader.getParameters());

            if (args.length < 2) {
                System.out.print(figure.getDescription());
            } else {
                DescriptionWriter.writeDescription(args[1], figure.getDescription());
            }
        } catch (NoArgsException ex) {
            System.err.println("The input file name is not specified!");
        } catch (NotEnoughParametersException ex) {
            System.err.println("Not enough parameters in the input file for this figure!");
        } catch (WrongFigureNameException ex) {
            System.err.print("The specified figure name is wrong!");
        } catch (WrongParametersException ex) {
            System.err.println("The specified parameters are wrong!");
        } catch (FileException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
