package ru.cft.focusstart.ryazantsev.exception;

public class FileException extends Exception {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
