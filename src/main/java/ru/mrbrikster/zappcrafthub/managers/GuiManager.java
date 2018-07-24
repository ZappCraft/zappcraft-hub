package ru.mrbrikster.zappcrafthub.managers;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.mrbrikster.zappcrafthub.Main;

public class GuiManager {

    private final Main main;

    /**
     * @param main Plugin instance
     */
    public GuiManager(Main main) {
        this.main = main;
    }

    /**
     * Creates gui for player
     * @param player Bukkit-player
     * @return Created gui
     */
    Gui getGui(Player player) {
        return new MenuGui(player, main);
    }

    public static class MenuGui extends Gui {

        private static final MenuScheme MENU_SCHEME = new MenuScheme()
                .mask("011111110")
                .mask("011111110")
                .mask("011111110")
                .mask("011111110");
        private final Main main;

        MenuGui(Player player, Main main) {
            super(player, 4, "Меню сервера");
            this.main = main;
        }

        @Override
        public void redraw() {
            if (isFirstDraw()) {
                MenuPopulator menuPopulator = MENU_SCHEME.newPopulator(this);
                main.getServerManager().getRegisteredServers().forEach(server -> {
                   menuPopulator.accept(ItemStackBuilder
                            .of(Material.DIAMOND_SWORD)
                            .name("&cПерейти на &6" + server.getName().toUpperCase())
                            .lore("&fОнлайн: &e" + server.getOnline() + ".")
                            .build(() -> {
                                close();
                                main.getQueue().sendToGame(getPlayer());
                            }));
                });
            }
        }

    }

}
