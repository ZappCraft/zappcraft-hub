package ru.mrbrikster.zappcrafthub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.lucko.helper.promise.Promise;

@AllArgsConstructor
public class Server {

    private final Main main;
    @Getter private final String name;
    @Getter private int online;

    /**
     * @param main Plugin instance
     * @param name Server name
     */
    public Server(Main main, String name) {
        this.main = main;
        this.name = name;
    }

    public void update() {
        try {
            Promise<Integer> promise = main.getBungeeCord().playerCount(name.toLowerCase());
            online = promise.get();
            promise.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
