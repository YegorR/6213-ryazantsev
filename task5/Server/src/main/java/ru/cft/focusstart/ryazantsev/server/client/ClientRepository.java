package ru.cft.focusstart.ryazantsev.server.client;

import ru.cft.focusstart.ryazantsev.common.Message;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ClientRepository {
    private Set<Client> clients = new CopyOnWriteArraySet<>();
    private Set<String> names = new HashSet<>();

    public void addClient(Client client) {
        clients.add(client);
    }

    public boolean setName(Client client, String name) {
        if (names.contains(name)) {
            return false;
        }
        names.add(name);
        client.setName(name);
        return true;
    }

    public void removeClient(Client client) {
        if (client.getName() != null) {
            names.remove(client.getName());
        }
        clients.remove(client);
    }

    public Iterator<Client> getClientIterator() {
        return clients.iterator();
    }

    public void writeEveryoneWithName(Message message) throws IOException {
        for (Client client : clients) {
            if (client.getName() != null) {
                client.write(message);
            }
        }
    }

    public void closeEveryone() throws IOException {
        for (Client client : clients) {
            client.close();
        }
    }
}


