package rezscripts.core.factions.upgrade;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import rezscripts.core.factions.P;
import rezscripts.core.factions.util.ItemBuilder;
import rezscripts.core.factions.util.ItemUtils;
import rezscripts.core.factions.util.manager.AbstractManagerCore;
import rezscripts.core.factions.util.manager.ManagerInstances;

public class UpgradeManager extends AbstractManagerCore {

	private ArrayList<FUpgrade> upgradeList;
	private ItemStack upgradeToken;

	public UpgradeManager(P pl) {
		super(pl);
	}

	@SuppressWarnings("serial")
	@Override
	public void initialize() {
		
		setUpgradeToken(new ItemBuilder(Material.DIAMOND).setName("&2&lFaction Upgrade Token").addGlow().setLore(ItemUtils.buildLore("","&aThis token is used to upgrade","&ayour faction using the &2/f upgrade", "&amenu. Obtain these rare tokens by", "&acompleting server events!")).toItemStack());
		
		setUpgradeList(new ArrayList<FUpgrade>());

//		getUpgradeList().add(new FUpgrade(0, "Spawn Speed Multiplier", 5, 5, Material.SLIME_BALL, ItemUtils.buildLore("",
//				"&aThis upgrade will increase the",
//				"&arate that mobs spawn from spawners",
//				"&ain your faction's land!")
//				, new HashMap<Integer, Integer>() {
//			{
//				put(1, 50);
//				put(2, 75);
//				put(3, 125);
//				put(4, 250);
//				put(5, 500);
//			}
//		}));

		getUpgradeList().add(new FUpgrade(0, "Mobs Spawned Multiplier", 7, 25,Material.MONSTER_EGG, ItemUtils.buildLore("",
				"&aThis upgrade will increase the",
				"&anumber of mobs that spawn from spawners",
				"&aper cycle in your faction's land!")
				,new HashMap<Integer, Integer>() {
			{
				put(1, 50);
				put(2, 75);
				put(3, 125);
				put(4, 250);
				put(5, 500);
				put(6, 750);
				put(7, 1000);
			}
		}));

		getUpgradeList().add(new FUpgrade(1, "Mob Drop Multiplier", 6, 25, Material.BLAZE_ROD, ItemUtils.buildLore("",
				"&aThis upgrade will increase the",
				"&anumber of drops a mob will drop",
				"&aupon death in your faction's land!")
				, new HashMap<Integer, Integer>() {
			{
				put(1, 75);
				put(2, 100);
				put(3, 150);
				put(4, 225);
				put(5, 550);
				put(6, 750);
			}
		}));

		getUpgradeList().add(new FUpgrade(2, "XP Drop Multiplier", 5,10,Material.EXP_BOTTLE, ItemUtils.buildLore("",
				"&aThis upgrade will increase the",
				"&anumber of xp orbs a mob will drop",
				"&aupon death in your faction's land!"), new HashMap<Integer, Integer>() {
			{
				put(1, 100);
				put(2, 125);
				put(3, 200);
				put(4, 250);
				put(5, 600);
			}
		}));
		
		P.p.log("Loaded " + getUpgradeList().size() + " Faction Upgrades");
	}

	public FUpgrade getUpgradeById(int i) {
		for (FUpgrade up : this.getUpgradeList()) {
			if (up.getId() == i) {
				return up;
			}
		}
		return null;
	}

	
	public ArrayList<FUpgrade> getUpgradeList() 
	{
		return this.upgradeList;
	}
	
	public ArrayList<FUpgrade> getUpgradeList(int page) 
	{
		int perPage = 3;
		int start = 0;
		int finish = start + perPage;
		
		if(page > 1) {
			start = 0 + (4*(page-1));
			finish = start + perPage;
		}
		
		
		//page 1 = 0-4 
		//page 2 = 5-9 
		
		ArrayList<FUpgrade> list = new ArrayList<FUpgrade>();
		
		for (int i = start; i < finish+1; i++) {
			if (this.upgradeList.size() > i){
				//has the upgrade
				list.add(getUpgradeById(i));
			}
		}
		
		return list;
	}

	void setUpgradeList(ArrayList<FUpgrade> upgradeList) {
		this.upgradeList = upgradeList;
	}

	public ItemStack getUpgradeToken() {
		return upgradeToken;
	}

	public void setUpgradeToken(ItemStack upgradeToken) {
		this.upgradeToken = upgradeToken;
	}

	public static int getTokensInPlayerInventory(Player p) {
		Inventory i = p.getInventory();
		int count = 0;
		
		for (ItemStack item : i) {
			if (item == null) {
				continue;
			}
			
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasDisplayName()) {
					if (item.getItemMeta().getDisplayName().equals(ManagerInstances.getInstance(UpgradeManager.class).getUpgradeToken().getItemMeta().getDisplayName())) {
						count += item.getAmount();
					}
				}
			}
		}
		
		return count;
	}
	
}
