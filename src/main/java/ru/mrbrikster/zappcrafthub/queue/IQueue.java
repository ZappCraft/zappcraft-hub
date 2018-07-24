package ru.mrbrikster.zappcrafthub.queue;

import org.bukkit.entity.Player;

public interface IQueue {

    void sendToGame(Player player);

    class QueueStatus {
        static final int DISCONNECT = -1;
        static final int WAITING = 0;
        static final int AVAILABLE = 1;
    }

    int getQueueStatus();

}
