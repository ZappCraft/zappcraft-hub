package ru.mrbrikster.zappcrafthub;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

    /**
     * Colorizes array of strings
     * @param strings Strings array to colorize
     * @return Colorized array
     */
    public static String[] colorize(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = ChatColor.translateAlternateColorCodes('&', strings[i]);
        }

        return strings;
    }

    /**
     * Compares two itemStacks
     * @param itemStack1 First itemStack
     * @param itemStack2 Second itemStack
     * @return Equals itemStacks or not
     */
    public static boolean equals(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack1 == null || itemStack2 == null)
            return false;

        if (itemStack1.getItemMeta() == null &&
                itemStack2.getItemMeta() == null) {
            return itemStack1.getType().equals(itemStack2.getType());
        }

        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        ItemMeta itemMeta2 = itemStack2.getItemMeta();

        if (itemMeta1 == null || itemMeta2 == null) {
            return false;
        }

        if (itemMeta1.getDisplayName() != null) {
            if (!itemMeta1.getDisplayName().equals(itemMeta2.getDisplayName()))
                return false;
        } else {
            if (itemMeta2.getDisplayName() != null)
                return false;
        }

        if (itemMeta1.getLore() != null) {
            return itemMeta1.getLore().equals(itemMeta2.getLore());
        } else {
            return itemMeta2.getLore() != null;
        }
    }

}
