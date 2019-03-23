package rezscripts.core.factions.menus;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.upgrade.FUpgrade;
import rezscripts.core.factions.upgrade.UpgradeManager;
import rezscripts.core.factions.util.ItemBuilder;
import rezscripts.core.factions.util.ItemUtils;
import rezscripts.core.factions.util.manager.ManagerInstances;
import rezscripts.core.factions.util.manager.menu.MenuItem;
import rezscripts.core.factions.util.manager.menu.MenuManager;
import rezscripts.core.factions.zcore.util.TL;

public class FupgradeMenu {

	public static void openMenu(Player p, Faction f, int page) {

		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		int row;
		int col;

		row = 0;
		col = 0;

		for (FUpgrade up : ManagerInstances.getInstance(UpgradeManager.class).getUpgradeList(page)) {
			
			if (up == null) {
				continue;
			}
			
			ItemStack i = new ItemBuilder(up.getShowMaterial()).setName("&a&l" + up.getName())
					.setLore(up.getDescription()).addGlow().toItemStack();

			list.add(new MenuItem(row, col, i, new Runnable() {
				@Override
				public void run() {
				}
			}));

			// add upgrades
			int upRow = row;
			int upCol = 1;

			if (!f.getUpgrades().containsKey(up.getId())) {
				continue;
			}

			int currentTier = f.getUpgrades().get(up.getId());

			for (int it = 0; it < up.getMaxUpgrade(); it++) {

				int level = it + 1;

				ItemBuilder i2 = new ItemBuilder(Material.STAINED_GLASS_PANE);
				if (currentTier + 1 == level) {
					// next to unlock
					i2.setDurability((short) 1);
					i2.setName("&6&lTier " + ItemUtils.tierToRoman(level));
					i2.setLore(ItemUtils.buildLore("", "&6Multiplier: &e" + level * up.getMultiplier() + "&e%",
							"&6Cost: &e" + up.getCostList().get(level) + "&e Faction Tokens", "",
							"&6<Click to purchase upgrade>"));
					list.add(new MenuItem(upRow, upCol, i2.toItemStack(), new Runnable() {
						@Override
						public void run() {
							if (f.getFPlayerAdmin().getPlayer() != p) {
								p.closeInventory();
								p.sendMessage(ChatColor.translateAlternateColorCodes('&',
										TL.COMMAND_UPGRADE_NEEDADMIN.toString())); // (TL.COMMAND_UPGRADE_NEEDADMIN.toString());
								return;
							} else {
								// final check before buy that someone else hasn't already bought it
								if (f.getUpgrades().get(up.getId()) == level) {
									openMenu(p, f, page);
									return;
								}

								if (UpgradeManager.getTokensInPlayerInventory(p) >= getUpgradeCost(up, f)) {
									// TODO:BUY
									ItemUtils.removeItems(p,
											ManagerInstances.getInstance(UpgradeManager.class).getUpgradeToken(),
											getUpgradeCost(up, f));
									
									f.getUpgrades().put(up.getId(), level);
									
//									p.sendMessage(up.getName());

									p.closeInventory();
									
//									p.sendMessage(up.getName());

									openMenu(p, f, page);

//									p.sendMessage(up.getName());

									f.getUpgrades().put(up.getId(), level);
									p.closeInventory();
									openMenu(p, f, page);

									for (FPlayer fp : f.getFPlayers()) {
										Player pla = fp.getPlayer();

										if (pla.isOnline()) {
											pla.playSound(pla.getLocation(), Sound.LEVEL_UP, 1f, 1f);
											pla.sendMessage(ChatColor.translateAlternateColorCodes('&',
													"&a&l" + p.getName()
															+ "&a&l has just purchased the faction upgrade: &6"
															+ up.getName() + " &f" + ItemUtils.tierToRoman(level)
															+ "&a&l for your faction!"));
										} else {
											continue;
										}
									}
									return;
								} else {
									// can't affort
									p.closeInventory();
									p.sendMessage(ChatColor.translateAlternateColorCodes('&',
											"&cYou cannot afford this upgrade!")); // (TL.COMMAND_UPGRADE_NEEDADMIN.toString());
									p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1f, 1f);
									return;
								}
							}
						}
					}));
				} else if (currentTier < level) {
					// not unlocked
					i2.setDurability((short) 15);
					i2.setName("&8&lTier " + ItemUtils.tierToRoman(level) + " &8&l(&c&lLOCKED&8&l)");
					i2.setLore(ItemUtils.buildLore("", "&8Multiplier: &8" + level * up.getMultiplier() + "&8%",
							"&8Cost: &8" + up.getCostList().get(level) + "&8 Faction Tokens", "",
							"&8<&cRequires Tier " + ItemUtils.tierToRoman(level - 1) + "&8>"));
					list.add(new MenuItem(upRow, upCol, i2.toItemStack(), new Runnable() {
						@Override
						public void run() {
						}
					}));
				} else {
					// unlocked
					i2.setDurability((short) 5);
					i2.addGlow();
					i2.setName("&2&lTier " + ItemUtils.tierToRoman(level) + " &8&l(&a&lUNLOCKED&8&l)");
					i2.setLore(ItemUtils.buildLore("", "&2Multiplier: &a" + level * up.getMultiplier() + "&a%",
							"&2Cost: &a" + up.getCostList().get(level) + "&a Faction Tokens", "",
							"&8<&aAlready Unlocked&8>"));
					list.add(new MenuItem(upRow, upCol, i2.toItemStack(), new Runnable() {
						@Override
						public void run() {
						}
					}));
				}
				upCol++;
			}

			row++;
		}

		for (int i = 0; i < 9; i++) {
			ItemBuilder item3 = null;
			// 3 & 5
			if (i == 3) {
				// back
				item3 = new ItemBuilder(Material.REDSTONE_BLOCK).setDurability((short) 0).setName("&7<< Back Page");
				
				list.add(new MenuItem(5, i, item3.toItemStack(), new Runnable() {
					@Override
					public void run() {
						if (page == 1) {
							return;
						}else {
							openMenu(p, f, page - 1);
							return;
						}
					}
				}));
			}
			else if (i == 5) {
				// forwarrd
				item3 = new ItemBuilder(Material.EMERALD_BLOCK).setDurability((short) 0).setName("&7Next Page >>");
				
				list.add(new MenuItem(5, i, item3.toItemStack(), new Runnable() {
					@Override
					public void run() {
						if (ManagerInstances.getInstance(UpgradeManager.class).getUpgradeList(page + 1).isEmpty()) {
							//no page +1
							return;
						}else {
							openMenu(p, f, page + 1);
							return;
						}
					}
				}));
			}
			
			if (item3 == null) {
				continue;
			}
		}

		p.openInventory(MenuManager.createMenu(p, "Upgrades : " + f.getTag() + " : P-" + page, 6, list));
	}

	protected static int getUpgradeCost(FUpgrade up, Faction f) {
		return up.getCostList().get(f.getUpgrades().get(up.getId()) + 1);
	}

}
