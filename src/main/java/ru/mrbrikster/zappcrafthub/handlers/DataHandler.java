package ru.mrbrikster.zappcrafthub.handlers;

import org.bukkit.entity.Player;
import org.school.client.cache.Caches;
import org.school.client.data.players.network.HandledPlayer;

public class DataHandler {

    /**
     * Get player of Octavia data-server
     * @param player Bukkit-player
     * @return Player of Octavia data-server
     */
    private static HandledPlayer getOctaviaPlayer(Player player) {
        return Caches.players().get(player.getUniqueId());
    }

    /**
     * Get player GameTokens
     * @param player Bukkit-player
     * @return Player balance
     */
    public static int getDonateMoney(Player player) {
        HandledPlayer handledPlayer = getOctaviaPlayer(player);

        if (handledPlayer == null)
            return 0;

        return handledPlayer.donateMoney().get();
    }

    /**
     * Withdraw player GameTokens
     * @param player Bukkit-player
     * @param donateMoney Value of GameTokens to withdraw
     * @return Was transaction successful
     */
    public static boolean withdrawDonateMoney(Player player, int donateMoney) {
        HandledPlayer handledPlayer = getOctaviaPlayer(player);

        if (handledPlayer == null)
            return false;

        return handledPlayer.donateMoney().withdraw(donateMoney);
    }

}
