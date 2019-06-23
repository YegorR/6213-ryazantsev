package ru.cft.focusstart.ryazantsev.server.client;

import java.util.HashSet;
import java.util.Set;

public class ClientRepository {
    private Set<Client> clients = new HashSet<>();
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

    public Client[] getClients() {
        return clients.toArray(new Client[0]);
    }


}


