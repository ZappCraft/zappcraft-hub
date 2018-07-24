package ru.mrbrikster.zappcrafthub.managers;

import lombok.Getter;
import ru.mrbrikster.zappcrafthub.Main;
import ru.mrbrikster.zappcrafthub.Server;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {

    private final Main main;
    @Getter private final List<Server> registeredServers;

    public ServerManager(Main main) {
        this.main = main;
        this.registeredServers = new ArrayList<>();

        this.registerServer(new Server(main, "fn-1"));
    }

    private void registerServer(Server server) {
        this.registeredServers.add(server);
    }

    public void unregisterServer(Server server) {
        this.registeredServers.remove(server);
    }

    public void unregisterAll() {
        this.registeredServers.clear();
    }
    
}
