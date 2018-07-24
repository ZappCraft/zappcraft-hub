package ru.mrbrikster.zappcrafthub.managers;

import me.lucko.helper.text.Text;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import ru.mrbrikster.zappcrafthub.Main;

public class EventManager implements Listener {

    public EventManager(Main main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        playerJoinEvent.setJoinMessage(null);

        Player player = playerJoinEvent.getPlayer();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.setTotalExperience(0);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.sendTitle("", "", 0, 0 , 0);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        playerQuitEvent.setQuitMessage(null);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getEntity().getType().equals(EntityType.PLAYER)) {
            entityDamageEvent.setDamage(0);
            entityDamageEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent playerPickupItemEvent) {
        playerPickupItemEvent.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent foodLevelChangeEvent) {
        foodLevelChangeEvent.setFoodLevel(20);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent playerDropItemEvent) {
        playerDropItemEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent playerSwapHandItemsEvent) {
        playerSwapHandItemsEvent.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        blockPlaceEvent.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (!(entity.getType().equals(EntityType.PLAYER) && event.getHand().equals(EquipmentSlot.HAND)))
            return;

        player.setPassenger(entity);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_GUITAR, 1, 1);
        ((Player) entity).playSound(player.getLocation(), Sound.BLOCK_NOTE_GUITAR, 1, 1);
        player.sendMessage(Text.colorize("&6Вы посадили к себе на плечи игрока " + entity.getName() + "."));
        entity.sendMessage(Text.colorize("&6Игрок " + player.getName() + " посадил вас на плечи."));
    }

    @EventHandler
    public void onPlayerLaunch(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.LEFT_CLICK_AIR))
            return;

        Player player = event.getPlayer();
        Entity passenger = player.getPassenger();

        if (passenger != null && passenger.getType().equals(EntityType.PLAYER)) {
            passenger.getVehicle().eject();

            Vector direction = player.getLocation().getDirection();
            passenger.setVelocity(direction.multiply(2));
            passenger.setFallDistance(-10000.0F);
            player.sendMessage(Text.colorize("&eВы бросили игрока " + passenger.getName() + "."));
            passenger.sendMessage(Text.colorize("&eВас сбросили с плеч."));

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BELL, 1, 1);
            ((Player) passenger).playSound(player.getLocation(), Sound.BLOCK_NOTE_BELL, 1, 1);
        }
    }

}
