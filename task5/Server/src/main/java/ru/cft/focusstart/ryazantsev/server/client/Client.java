package ru.cft.focusstart.ryazantsev.server.client;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

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

    public boolean readyToRead() throws IOException {
        return reader.ready();
    }

    void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return true; //ЗАГЛУШКА
    }

    public String getName() {
        return name;
    }

    public String read() throws IOException {
        return reader.readLine();
    }

    public void write(String message) {
        writer.println(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return socket.equals(client.socket) &&
                reader.equals(client.reader) &&
                writer.equals(client.writer) &&
                Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, reader, writer, name);
    }
}
