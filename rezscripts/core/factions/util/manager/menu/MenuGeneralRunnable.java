package rezscripts.core.factions.util.manager.menu;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public abstract class MenuGeneralRunnable {

    public void onClick(Player p, ItemStack item, int slot) {
        if (p != null && item != null && item.getType() != Material.AIR)
            execute(p, item, slot);
    }

    public abstract void execute(final Player p, ItemStack item, int slot);

}