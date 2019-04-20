package ru.cft.focusstart.ryazantsev;

import ru.cft.focusstart.ryazantsev.exception.IOOutputException;
import ru.cft.focusstart.ryazantsev.exception.OutputFileNotFoundException;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class DescriptionWriter {
    public static void writeDescription(String filename, String description) throws OutputFileNotFoundException, IOOutputException {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(description);
        } catch (FileNotFoundException ex) {
            throw new OutputFileNotFoundException();
        } catch (IOException ex) {
            throw new IOOutputException();
        }
    }
}
