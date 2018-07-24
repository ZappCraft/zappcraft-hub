package ru.mrbrikster.zappcrafthub.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import ru.mrbrikster.zappcrafthub.Main;
import ru.mrbrikster.zappcrafthub.shop.Gun;
import ru.mrbrikster.zappcrafthub.shop.Shop;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {

    /**
     * @param main PluginInstance
     */
    public ShopManager(Main main) {
        FileConfiguration config = main.loadConfig("guns.yml");

        for (String key : config.getConfigurationSection("guns").getKeys(false)) {
            ConfigurationSection gunSection = config.getConfigurationSection("guns." + key);

            Map<Shop.TimeUnit, Integer> prices = new HashMap<>();

            prices.put(Shop.TimeUnit.HOURS_36, gunSection.getInt("prices.36h"));
            prices.put(Shop.TimeUnit.DAYS_7, gunSection.getInt("prices.7d"));
            prices.put(Shop.TimeUnit.DAYS_14, gunSection.getInt("prices.14d"));
            prices.put(Shop.TimeUnit.DAYS_30, gunSection.getInt("prices.30d"));

            // Creates shop for specified gun
            new Shop(main,
                    new Gun(key.toLowerCase(),
                            gunSection.getString("displayName"),
                            gunSection.getInt("id"),
                            gunSection.getInt("durability")),
                    prices, new Location(Bukkit.getWorld("world"),
                        gunSection.getDouble("location.x"),
                        gunSection.getDouble("location.y"),
                        gunSection.getDouble("location.z")));
        }
    }

}
