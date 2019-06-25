package ru.cft.focusstart.ryazantsev.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.ryazantsev.common.Message;
import ru.cft.focusstart.ryazantsev.server.client.ClientRepository;
import ru.cft.focusstart.ryazantsev.server.exception.ServerChatException;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    public static void main(String[] args) {
        try {
            PropertyReader propertyReader = new PropertyReader("server.properties");
            int port = propertyReader.readPort();
            ClientRepository clientRepository = new ClientRepository();
            Thread clientHandlerThread = new Thread(new ClientHandler(clientRepository));
            clientHandlerThread.start();
            SocketAccepter socketAccepter = new SocketAccepter(port, clientRepository);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    clientHandlerThread.interrupt();
                    socketAccepter.stop();
                    clientRepository.writeEveryoneWithName(new Message(Message.MessageType.SERVER_OUT));
                    clientRepository.closeEveryone();
                    logger.info("Server is closed. Bye-bye.");
                } catch (Exception ex) {
                    logger.error("Shutdown error", ex);
                }
            }));
            logger.info("Start server on port {}", port);
            socketAccepter.runServer();
        } catch (ServerChatException ex) {
            logger.error("Fatal server error", ex);
        }


    }
}
