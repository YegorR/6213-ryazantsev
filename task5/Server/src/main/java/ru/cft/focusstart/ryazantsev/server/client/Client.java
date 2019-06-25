package ru.cft.focusstart.ryazantsev.server.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.ryazantsev.common.Message;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String name = null;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public boolean isReady() throws IOException {
        return reader.ready();
    }

    public String getName() {
        return name;
    }

    public Message read() throws IOException {
        return OBJECT_MAPPER.readValue(reader.readLine(), Message.class);
    }

    public void write(Message message) throws IOException {
        writer.println(OBJECT_MAPPER.writeValueAsString(message));
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

    void setName(String name) {
        this.name = name;
    }

    public void close() throws IOException {
        socket.close();
    }
}
