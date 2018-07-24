package ru.mrbrikster.zappcrafthub.shop;

import lombok.Getter;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.text.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.mrbrikster.zappcrafthub.handlers.DataHandler;

import java.util.Map;

public class Gun {

    private static final MenuScheme MENU_SCHEME = new MenuScheme()
            .mask("000000000")
            .mask("010101010")
            .mask("000000000");

    @Getter private final String name;
    @Getter private final String displayName;
    @Getter private final int id;
    @Getter private final int durability;

    /**
     * @param name Gun name in CrackShot
     * @param displayName Display name of gun
     * @param id Item ID in game
     * @param durability Item sub-ID in game
     */
    public Gun(String name, String displayName, int id, int durability) {
        this.name = name;
        this.displayName = displayName;
        this.id = id;
        this.durability = durability;
    }

    /**
     * @param player Player to open menu
     * @param prices Prices of gun for time units
     */
    void openMenu(Player player, Map<Shop.TimeUnit, Integer> prices) {
        new GunMenu(player, prices).open();
    }

    public class GunMenu extends Gui {
        private final Map<Shop.TimeUnit, Integer> prices;

        /**
         * @param player Player to open menu
         * @param prices Prices of gun for time units
         */
        GunMenu(Player player, Map<Shop.TimeUnit, Integer> prices) {
            super(player, 3, "Меню сервера");

            this.prices = prices;
        }

        @Override
        public void redraw() {
            if (isFirstDraw()) {
                MenuPopulator menuPopulator = MENU_SCHEME.newPopulator(this);

                accept(menuPopulator, Shop.TimeUnit.HOURS_36,
                        prices.get(Shop.TimeUnit.HOURS_36));
                accept(menuPopulator, Shop.TimeUnit.DAYS_7,
                        prices.get(Shop.TimeUnit.DAYS_7));
                accept(menuPopulator, Shop.TimeUnit.DAYS_14,
                        prices.get(Shop.TimeUnit.DAYS_14));
                accept(menuPopulator, Shop.TimeUnit.DAYS_30,
                        prices.get(Shop.TimeUnit.DAYS_30));
            }
        }

        /**
         * Adds icon to menu
         * @param menuPopulator Menu populator of this menu
         * @param timeUnit Time unit of this offer
         * @param price Price of this offer
         */
        private void accept(MenuPopulator menuPopulator, Shop.TimeUnit timeUnit, Integer price) {
            String timeUnitName = null;

            switch (timeUnit) {
                case HOURS_36: timeUnitName = "36 часов"; break;
                case DAYS_7: timeUnitName = "7 дней"; break;
                case DAYS_14: timeUnitName = "14 дней"; break;
                case DAYS_30: timeUnitName = "30 дней"; break;
            }

            menuPopulator.accept(ItemStackBuilder
                    .of(Material.CHEST)
                    .name(String.format("&e%s &f- &6%d GAMETOKEN", timeUnitName, price))
                    .lore("&aНажмите, чтобы приобрести.")
                    .build(() -> {
                        if (DataHandler.withdrawDonateMoney(getPlayer(), price)) {
                            getPlayer().sendMessage(Text.colorize("&aУспешное приобретение."));
                        } else getPlayer().sendMessage(Text.colorize("&cУ вас недостаточно GAMETOKEN."));

                        close();
                    }));
        }

    }

}
