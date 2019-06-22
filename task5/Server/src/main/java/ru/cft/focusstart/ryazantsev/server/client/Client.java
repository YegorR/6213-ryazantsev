package ru.cft.focusstart.ryazantsev.server.client;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String name = null;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void write(String message) {
        writer.println(message);
    }

    public boolean readyToRead() throws IOException {
        return reader.ready();
    }

    public String read() throws IOException {
        return reader.readLine();
    }

    public boolean isConnected() {
        return true; //ЗАГЛУШКА
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
