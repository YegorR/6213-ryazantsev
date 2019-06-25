package ru.cft.focusstart.ryazantsev.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.ryazantsev.common.Message;
import ru.cft.focusstart.ryazantsev.server.client.Client;
import ru.cft.focusstart.ryazantsev.server.client.ClientRepository;

import static ru.cft.focusstart.ryazantsev.common.Message.MessageType.*;

import java.io.IOException;
import java.util.Iterator;

public class ClientHandler implements Runnable {

    private ClientRepository clientRepository;
    private final static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    public ClientHandler(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run() {
        boolean interrupted = false;
        while (!interrupted) {
            try {
                Iterator<Client> iterator = clientRepository.getClientIterator();
                while (iterator.hasNext()) {
                    Client client = iterator.next();
                    try {
                        if (client.isReady()) {
                            logger.debug("Some client is ready");
                            handleMessage(client);
                        }
                    } catch (IOException ex) {
                        logger.warn("IO error of ClientHandler", ex);
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                interrupted = true;
            }
        }
    }

    private void handleMessage(Client client) throws IOException {
        if (client.getName() == null) {
            handleNewClient(client);
        } else {
            handleOldClient(client);
        }

    }

    private void handleNewClient(Client client) throws IOException {
        Message message = client.read();
        logger.debug("Message is received: type = {}, text = {}, name = {}",
                message.getMessageType(), message.getText(), message.getName());
        Message.MessageType messageType = message.getMessageType();
        if (messageType != NEW_MEMBER) {
            client.write(new Message(ERROR));
            return;
        }
        String name = message.getName();
        if (name == null) {
            client.write(new Message(ERROR));
            return;
        }
        name = name.trim();
        if (clientRepository.setName(client, name)) {
            client.write(new Message(SUCCESSFUL_CONNECT));
            clientRepository.writeEveryoneWithName(message);
            Iterator<Client> iterator = clientRepository.getClientIterator();
            while (iterator.hasNext()) {
                Client oldClient = iterator.next();
                if (oldClient.getName() != null) {
                    Message sendMessage = new Message(OLD_MEMBER);
                    sendMessage.setName(oldClient.getName());
                    client.write(sendMessage);
                }
            }
            logger.info("New client \"{}\"", name);
        } else {
            client.write(new Message(BAD_NAME));
        }
    }

    private void handleOldClient(Client client) throws IOException {
        Message message = client.read();
        logger.debug("Message is received: type = {}, text = {}, name = {}",
                message.getMessageType(), message.getText(), message.getName());
        Message.MessageType messageType = message.getMessageType();
        switch (messageType) {
            case NEW_MEMBER:
            case BAD_NAME:
            case SUCCESSFUL_CONNECT:
            case SERVER_OUT:
                client.write(new Message(ERROR));
                break;
            case MESSAGE:
                message.setName(client.getName());
                clientRepository.writeEveryoneWithName(message);
                break;
            case GONE_MEMBER:
                message.setName(client.getName());
                clientRepository.removeClient(client);
                client.close();
                clientRepository.writeEveryoneWithName(message);
                logger.info("Client \"{}\" is gone", client.getName());
                break;
            case ERROR:
                logger.warn("Error message from client");
                break;
        }
    }
}
