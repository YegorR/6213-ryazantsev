package ru.cft.focusstart.ryazantsev.server;

import ru.cft.focusstart.ryazantsev.server.exception.ServerChatException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertyReader {
    private String filename;

    PropertyReader(String filename) {
        this.filename = filename;
    }

    int readPort() throws ServerChatException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = ChatServer.class.getClassLoader().getResourceAsStream(filename)) {
            if (propertiesStream == null) {
                throw new ServerChatException(String.format("The resource %s is not found", filename));
            }
            properties.load(propertiesStream);
        } catch (IOException ex) {
            throw new ServerChatException(String.format("IO error of reading %s", filename), ex);
        } catch (IllegalArgumentException ex) {
            throw new ServerChatException(String.format("Unicode error of %s", filename), ex);
        }

        String portString =  properties.getProperty("server.port");
        if (portString == null) {
           throw new ServerChatException(String.format("Property \"server.port\" is not found in %s", filename));
        }
        try {
            return Integer.valueOf(portString);
        } catch (NumberFormatException ex) {
            throw new ServerChatException(String.format("Property \"server.port\" is not a valid integer in %s",
                    filename));
        }
    }
}
