package ru.cft.focusstart.ryazantsev.server;

import ru.cft.focusstart.ryazantsev.server.client.Client;
import ru.cft.focusstart.ryazantsev.server.client.ClientRepository;
import ru.cft.focusstart.ryazantsev.server.exception.ServerChatException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketAccepter {
    private ClientRepository clientRepository;
    private ServerSocket serverSocket;

    public SocketAccepter(int port, ClientRepository clientRepository) throws ServerChatException {
        this.clientRepository = clientRepository;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            throw new ServerChatException("IO error of creating of server socket", ex);
        } catch (IllegalArgumentException ex) {
            throw new ServerChatException("Illegal port for server socket", ex);
        }

    }

    public void runServer() throws ServerChatException {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket);
                clientRepository.addClient(client);
            } catch (IOException ex) {
                throw new ServerChatException("IO error of server socket ", ex);
            }
        }
    }

    public void stop() throws ServerChatException {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            throw new ServerChatException("IO error of server socket closing", ex);
        }
    }
}
