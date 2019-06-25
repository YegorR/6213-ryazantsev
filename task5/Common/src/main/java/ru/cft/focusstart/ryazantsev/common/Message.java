package ru.cft.focusstart.ryazantsev.common;

import java.util.Date;

public class Message {
    public enum MessageType {
        SUCCESSFUL_CONNECT, BAD_NAME, SERVER_OUT, MESSAGE, NEW_MEMBER, OLD_MEMBER, GONE_MEMBER, ERROR
    }

    private String text;
    private String name;
    private Date date;
    private MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
        date = new Date();
    }

    public Message() {
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getName() {
        return name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
