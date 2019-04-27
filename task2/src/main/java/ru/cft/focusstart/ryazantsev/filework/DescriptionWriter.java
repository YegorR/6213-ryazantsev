package ru.cft.focusstart.ryazantsev.filework;


import ru.cft.focusstart.ryazantsev.exception.FileException;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class DescriptionWriter {
    public static void writeDescription(String filename, String description) throws FileException {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(description);
        } catch (FileNotFoundException ex) {
            throw new FileException("The output file is not found!");
        } catch (IOException ex) {
            throw new FileException("The IOException of the output file!");
        }
    }
}
