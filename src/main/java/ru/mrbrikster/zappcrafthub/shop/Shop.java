package ru.mrbrikster.zappcrafthub.shop;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.mrbrikster.zappcrafthub.Main;

import java.util.Map;

public class Shop implements Listener {

    private final Gun gun;
    private final Location location;
    private final Map<TimeUnit, Integer> prices;

    /**
     * @param main Plugin instance
     * @param gun Gun for sale
     * @param prices Prices of gun
     * @param location Location of the chest
     */
    public Shop(Main main, Gun gun, Map<TimeUnit, Integer> prices, Location location) {
        this.gun = gun;
        this.location = location;
        this.prices = prices;

        Hologram hologram = HologramsAPI.createHologram(main, location.clone().add(0, 4, 0));
        TouchHandler touchHandler = (handler) -> gun.openMenu(handler.getPlayer(), prices);

        @SuppressWarnings("all")
        ItemStack itemStack = new ItemStack(gun.getId());
        itemStack.setDurability((short) gun.getDurability());

        hologram.appendTextLine(colorize("&fОружие &e" + gun.getDisplayName())).setTouchHandler(touchHandler);
        hologram.appendTextLine(colorize("")).setTouchHandler(touchHandler);
        hologram.appendTextLine(colorize("&7Стоимость:")).setTouchHandler(touchHandler);

        for (TimeUnit timeUnit : TimeUnit.values()) {
            String timeUnitName = null;

            switch (timeUnit) {
                case HOURS_36: timeUnitName = "36 часов"; break;
                case DAYS_7: timeUnitName = "7 дней"; break;
                case DAYS_14: timeUnitName = "14 дней"; break;
                case DAYS_30: timeUnitName = "30 дней"; break;
            }

            hologram.appendTextLine(colorize(String.format("&f%s - &6%d GAMETOKEN.", timeUnitName, prices.get(timeUnit))))
                    .setTouchHandler(touchHandler);
        }

        hologram.appendTextLine(colorize("")).setTouchHandler(touchHandler);
        hologram.appendTextLine(colorize("&aНажмите для покупки.")).setTouchHandler(touchHandler);
        hologram.appendItemLine(itemStack).setTouchHandler(touchHandler);

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        if (!playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
                !playerInteractEvent.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;

        if (playerInteractEvent.getClickedBlock() != null &&
                playerInteractEvent.getClickedBlock().getLocation()
                        .equals(location.getBlock().getLocation())) {
            playerInteractEvent.setCancelled(true);
            gun.openMenu(playerInteractEvent.getPlayer(), prices);
        }
    }

    /**
     * Colorizes the text
     * @param string String to colorize
     */
    private String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public enum TimeUnit {
        HOURS_36(129600),
        DAYS_7(604800),
        DAYS_14(1209600),
        DAYS_30(2592000);

        @Getter private final long time;

        /**
         * @param time Time in seconds
         */
        TimeUnit(long time) {
            this.time = time;
        }
    }

}
