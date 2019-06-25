package ru.cft.focusstart.ryazantsev.client.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.ryazantsev.common.Message;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class NetManager {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public NetManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    public void connect(String host, int port, Consumer<Boolean> connectListener) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                connectListener.accept(connect(host, port));
                return null;
            }
        }.execute();
    }

    private boolean connect(String address, int port) {
        try {
            socket = new Socket(address, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public void sendMessage(Message message, Consumer<Boolean> sendListener) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                sendListener.accept(sendMessage(message));
                return null;
            }
        }.execute();
    }

    private boolean sendMessage(Message message) {
        try {
            writer.println(OBJECT_MAPPER.writeValueAsString(message));
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public Message getMessage() throws IOException {
        return OBJECT_MAPPER.readValue(reader.readLine(), Message.class);
    }

    public boolean isReady() throws IOException {
        if (reader == null) {
            return false;
        }
        return reader.ready();
    }

    public void close() {
        try {
            sendMessage(new Message(Message.MessageType.GONE_MEMBER));
            reader = null;
            writer = null;
            socket.close();
        } catch (Exception ignored) {
        }
    }
}
