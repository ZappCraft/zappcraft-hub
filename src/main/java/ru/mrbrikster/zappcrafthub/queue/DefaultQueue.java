package ru.mrbrikster.zappcrafthub.queue;

import me.lucko.helper.text.Text;
import org.bukkit.entity.Player;
import ru.mrbrikster.zappcrafthub.Main;

public class DefaultQueue implements IQueue {

    private final Main main;

    public DefaultQueue(Main main) {
        this.main = main;
    }

    @Override
    public void sendToGame(Player player) {
        if (getQueueStatus() == QueueStatus.AVAILABLE) {
            // TODO Random server

            main.getBungeeCord().connect(player, "fn-1");

            player.sendMessage(Text.colorize("&cВы перешли на сервер fn-1."));
        }
    }

    @Override
    public int getQueueStatus() {
        return 1;
    }

}
