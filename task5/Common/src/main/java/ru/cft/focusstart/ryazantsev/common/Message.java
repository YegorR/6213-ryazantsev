package ru.cft.focusstart.ryazantsev.common;

import java.util.Date;

public class Message {
    public enum MessageType {
        TRY_CONNECT, SUCCESSFUL_CONNECT, BAD_NAME, OUT_CONNECT, MESSAGE, NEW_MEMBER, GONE_MEMBER, ERROR
    }

    private String text;
    private String name;
    private Date date;
    private MessageType messageType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
