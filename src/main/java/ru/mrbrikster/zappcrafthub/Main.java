package ru.mrbrikster.zappcrafthub;

import lombok.Getter;
import me.lucko.helper.messaging.bungee.BungeeCord;
import me.lucko.helper.messaging.bungee.BungeeCordImpl;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import ru.mrbrikster.zappcrafthub.managers.*;
import ru.mrbrikster.zappcrafthub.queue.DefaultQueue;
import ru.mrbrikster.zappcrafthub.queue.IQueue;

public class Main extends ExtendedJavaPlugin {

    @Getter private ItemManager itemManager;
    @Getter private GuiManager guiManager;
    @Getter private EventManager eventManager;
    @Getter private IQueue queue;
    @Getter private BungeeCord bungeeCord;
    @Getter private ServerManager serverManager;
    @Getter private ShopManager shopManager;

    @Override
    public void enable() {
        // Register BungeeCord channels
        this.bungeeCord = new BungeeCordImpl(this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Init managers
        this.queue = new DefaultQueue(this);
        this.itemManager = new ItemManager(this);
        this.guiManager = new GuiManager(this);
        this.eventManager = new EventManager(this);
        this.serverManager = new ServerManager(this);
        this.shopManager = new ShopManager(this);
    }

    @Override
    protected void disable() {
        serverManager.unregisterAll();
    }
}
