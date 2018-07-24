package ru.mrbrikster.zappcrafthub.managers;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.mrbrikster.zappcrafthub.Main;
import ru.mrbrikster.zappcrafthub.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemManager implements Listener {

    private List<Item> items;

    /**
     * ItemManager
     * @param main Plugin instance
     */
    public ItemManager(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
        this.items = new ArrayList<>();

        Collections.addAll(items,
                Item.builder()
                        .material(Material.COMPASS)
                        .name("&eМеню сервера")
                        .lore(new String[] {"&7Нажми на меня!"})
                        .clickHandler(((item, player) -> main.getGuiManager().getGui(player).open()))
                        .slot(0).build());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        player.getInventory().clear();

        for (Item item : items) {
            player.getInventory().setItem(item.getSlot(), item.toItemStack());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        if (!EquipmentSlot.HAND.equals(playerInteractEvent.getHand()))
            return;

        ItemStack itemStack = playerInteractEvent.getItem();
        if (itemStack == null)
            return;

        for (Item item : items) {
            if (Utils.equals(item.toItemStack(), playerInteractEvent.getItem())) {
                item.getClickHandler().onClick(item, playerInteractEvent.getPlayer());
                playerInteractEvent.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getClickedInventory() == null)
            return;

        if (inventoryClickEvent.getClickedInventory().getHolder()
                .equals(inventoryClickEvent.getWhoClicked()))
            inventoryClickEvent.setCancelled(true);
    }

    public static class Item {

        @Getter private Material material = Material.GOLD_INGOT;
        @Getter private String name = "&eСтандартный предмет";
        @Getter private String[] lore = new String[] {};
        @Getter private ClickHandler clickHandler = (item, player) -> player.sendMessage("Я - стандартный предмет.");
        @Getter private int slot = 0;

        /**
         * Private constructor
         */
        private Item() {}

        ItemStack toItemStack() {
            ItemStack itemStack = new ItemStack(material);

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
            itemMeta.setLore(Arrays.asList(lore));
            itemStack.setItemMeta(itemMeta);

            return itemStack;
        }

        public interface ClickHandler {

            void onClick(Item item, Player player);

        }

        static Builder builder() {
            return new Builder();
        }

        private static class Builder {

            private final Item item;

            private Builder() {
                this.item = new Item();
            }

            Builder material(Material material) {
                item.material = material;
                return this;
            }

            Builder name(String name) {
                item.name = ChatColor.translateAlternateColorCodes('&', name);
                return this;
            }

            Builder lore(String[] lore) {
                item.lore = Utils.colorize(lore);
                return this;
            }

            Builder clickHandler(ClickHandler clickHandler) {
                item.clickHandler = clickHandler;
                return this;
            }

            Builder slot(int slot) {
                item.slot = slot;
                return this;
            }

            Item build() {
                return item;
            }

        }

    }
}
