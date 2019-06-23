package ru.cft.focusstart.ryazantsev.server.exception;

public class ServerChatException extends Exception {
    public ServerChatException(String message) {
        super(message);
    }

    public ServerChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
