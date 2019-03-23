package rezscripts.core.factions.koth;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.FPlayers;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.util.MiscUtil;

public class KothPulse extends BukkitRunnable implements Listener {

	public Koth activeKoth;
	public ArrayList<Player> kothPlayers;

	public KothPulse() {
		kothPlayers = new ArrayList<Player>();
	}

	public void newKoth(Koth k) {
		k.setActive(true);
		k.setCapPlayer(null);
		k.setSecondsRemaining(600); // 10 mins
		k.setCap(0);
		this.activeKoth = k;
		this.kothPlayers = new ArrayList<Player>();

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&e&lKoth Event: &f&l" + k.getName() + " &e&lhas started"));
		}
	}

	@EventHandler
	public void playerMove(PlayerMoveEvent e) {

		if (activeKoth == null) {
			return;
		}
		
		if (!activeKoth.isActive()) {
			return;
		}

		if (e.getTo().getBlock() == e.getFrom().getBlock()) {
			return;
		}

		if (kothPlayers.contains(e.getPlayer())) {
			if (!activeKoth.getRegion().isWithinRegion(e.getPlayer())) {
				kothPlayers.remove(e.getPlayer());
				//reset if current capper
				if (activeKoth.getCapPlayer() != null) {
					if (activeKoth.getCapPlayer() == e.getPlayer()) {
						activeKoth.setCapPlayer(null);
					}
				}
			}
			return;
		}

		if (activeKoth.getRegion().isWithinRegion(e.getPlayer())) {
			kothPlayers.add(e.getPlayer());
		}
	}

	@Override
	public void run() {
		// quick player check

		if (activeKoth == null) {
			return;
		}

		if (!kothPlayers.isEmpty()) {
			for (Player p : kothPlayers) {
				if (!p.isOnline()) {
					kothPlayers.remove(p);
				}
			}
		}

		if (activeKoth.getCapPlayer() == null) {
			// random dude within koth
			if (!kothPlayers.isEmpty()) {	
			activeKoth.setCapPlayer(MiscUtil.randomArrayPlayer(kothPlayers));
			activeKoth.setCap(0);
			}
		}

		pulse();
		
	}

	public void pulse() {
		if (activeKoth.getSecondsRemaining() != 0) {
			int seconds = activeKoth.getSecondsRemaining();
			int cap = activeKoth.getCap();
			
//			for (Player p : Bukkit.getOnlinePlayers()) {
//				p.sendMessage(activeKoth.getCapPlayer() == null ? "Noone - 0%" : activeKoth.getCapPlayer().getName()
//						+ " - " 
//						+ activeKoth.getCap() + "%");
//			}
			
			//pulse
			
			if(activeKoth.getCapPlayer() != null 
					&& activeKoth.getRegion().isWithinRegion(activeKoth.getCapPlayer())) {
				//player != null and is in the region
			activeKoth.setCap(cap +1);
			}
			
			if (activeKoth.getCap() == 100) {
				//end koth (capped)
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(" ");
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&6&lThe KOTH event has been capped by: &e&l" 
					+ activeKoth.getCapPlayer().getName()));
					p.sendMessage(" ");
					
					//add koth to faction
					//+ reward
					
					try {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&a&l  + 1 &2&lKoth Reward Pouch"));
						p.getInventory().addItem(KothReward.getKothRewardItem());
					}catch(Exception e) {
						p.getWorld().dropItem(p.getEyeLocation(), KothReward.getKothRewardItem());
					}
					
					FPlayer fp = FPlayers.getInstance().getByPlayer(activeKoth.getCapPlayer());
					Faction f = fp.getFaction();
					
					if (f.isWarZone() || f.isSafeZone() || f.isWilderness()) {
						return;
					}else {
						f.addKothWin();
					}
					
					activeKoth = null;
				}
				return;
			}
			
			activeKoth.setSecondsRemaining(seconds-1);
			//
		}else {
			//end koth (out of time)
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(" ");
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&6&lThe KOTH event has ended. Noone capped in time!"));
				p.sendMessage(" ");
				
				activeKoth = null;
			}
			return;
		}
	}
	
}
