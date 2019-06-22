package ru.cft.focusstart.ryazantsev.server.exception;

public class WrongPropertyFileException extends Exception {
    public WrongPropertyFileException(String message) {
        super(message);
    }

    public WrongPropertyFileException(String message, Throwable causeExc) {
        super(message, causeExc);
    }
}
