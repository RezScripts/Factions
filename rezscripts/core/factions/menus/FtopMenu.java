package rezscripts.core.factions.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.FPlayers;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.P;
import rezscripts.core.factions.util.ItemBuilder;
import rezscripts.core.factions.util.ItemUtils;
import rezscripts.core.factions.util.manager.menu.MenuItem;
import rezscripts.core.factions.util.manager.menu.MenuManager;
import rezscripts.core.factions.zcore.util.TagUtil;

public class FtopMenu {

	public static void showMenu(Player p, List<Faction> subList, int page) {

		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		int row = 0;
		int col = 0;

		int rank = 1;
		for (Faction faction : subList) {
			row = getRowForRank(rank);
			col = getColForRank(rank);
			FPlayer fme = FPlayers.getInstance().getByPlayer(p);
			String fac = faction.getRelationTo(fme).getColor() + faction.getTag();

			ItemStack i = new ItemBuilder(Material.STAINED_GLASS_PANE, rank).setDurability(getColorRank(rank)).addGlow()
					.setName("&2&lRank: &a&l" + rank)
					.setLore(ItemUtils.buildLore("", "&2Faction: " + fac,
							"&2Value: &a" + TagUtil.parsePlain(faction, "{total-value}"),
							"    &8Land value      : " + TagUtil.parsePlain(faction, "{land-value}"),
							"    &8Balance Value  : " + TagUtil.parsePlain(faction, "{balance-value}"),
							"",
							"&6<Click to view more info on this faction>"))
					.toItemStack();

			MenuItem item = new MenuItem(row, col, i, new Runnable() {
				@Override
				public void run() {
					p.closeInventory();
					P.p.cmdBase.cmdShow.execute(p, new ArrayList<String>() {
						private static final long serialVersionUID = 1L;
						{
							add(faction.getTag());
						}
					});
					return;
				}
			});
			list.add(item);
			rank++;
		}

		p.openInventory(MenuManager.createMenu(p, "F-Top : Page-" + page, 4, list));
	}

	private static short getColorRank(int rank) {
		switch (rank) {
		case 1:
			return 5;
		case 2:
			return 4;
		case 3:
			return 4;
		case 4:
			return 2;
		case 5:
			return 2;
		case 6:
			return 2;
		case 7:
			return 14;
		case 8:
			return 14;
		case 9:
			return 14;
		case 10:
			return 14;
		default:
			return 0;
		}
	}

	private static int getColForRank(int rank) {
		switch (rank) {
		case 1:
			return 4;
		case 2:
			return 3;
		case 3:
			return 5;
		case 4:
			return 2;
		case 5:
			return 4;
		case 6:
			return 6;
		case 7:
			return 1;
		case 8:
			return 3;
		case 9:
			return 5;
		case 10:
			return 7;
		default:
			return 0;
		}
	}

	private static int getRowForRank(int rank) {
		switch (rank) {
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 1;
		case 4:
			return 2;
		case 5:
			return 2;
		case 6:
			return 2;
		case 7:
			return 3;
		case 8:
			return 3;
		case 9:
			return 3;
		case 10:
			return 3;
		default:
			return 0;
		}
	}

}
