package ru.cft.focusstart.ryazantsev.server;

import ru.cft.focusstart.ryazantsev.server.exception.WrongPropertyFileException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private String filename;

    public PropertyReader(String filename) {
        this.filename = filename;
    }

    public int readPort() throws WrongPropertyFileException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = ChatServer.class.getClassLoader().getResourceAsStream(filename)) {
            if (propertiesStream == null) {
                throw new WrongPropertyFileException(String.format("The resource %s is not found", filename));
            }
            properties.load(propertiesStream);
        } catch (IOException ex) {
            throw new WrongPropertyFileException(String.format("IO error of reading %s", filename), ex);
        } catch (IllegalArgumentException ex) {
            throw new WrongPropertyFileException(String.format("Unicode error of %s", filename), ex);
        }

        String portString =  properties.getProperty("server.port");
        if (portString == null) {
           throw new WrongPropertyFileException(String.format("Property \"server.port\" is not found in %s", filename));
        }
        try {
            return Integer.valueOf(portString);
        } catch (NumberFormatException ex) {
            throw new WrongPropertyFileException(String.format("Property \"server.port\" is not a valid integer in %s",
                    filename));
        }
    }
}
