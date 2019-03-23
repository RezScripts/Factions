package rezscripts.core.factions.util;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ItemUtils {

	public static ArrayList<String> buildLore(String... lines) {
		ArrayList<String> lore = new ArrayList<String>();

		for (String s : lines) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s));
		}

		return lore;
	}


    public static void removeItems(Player p, ItemStack item, int amount) {
    	
    	Inventory inventory = p.getInventory();
    	
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            
            if (is.hasItemMeta() 
            		&& is.getItemMeta().hasDisplayName() 
            		&& is.getItemMeta().getDisplayName()
            		.equals(item.getItemMeta().getDisplayName())) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    p.updateInventory();
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    p.updateInventory();
                    if (amount == 0) break;
                }
            }
        }
    }
    
    public static String tierToRoman(int tier) {
        switch (tier) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
        }
        return "??";
    }

}
