package rezscripts.core.factions.koth;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import rezscripts.core.factions.util.ItemBuilder;

public class KothReward {
	
	
	public static ItemStack getKothRewardItem() {
		ItemBuilder ib = new ItemBuilder(Material.INK_SACK);
		ib.addGlow();
		ib.setName("&2&lKoth Loot Bag &r&7(Right-Click)");
		
		
		return ib.toItemStack();
	}

}
