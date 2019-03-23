package rezscripts.core.factions.koth;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.FPlayers;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.P;
import rezscripts.core.factions.koth.regions.Region;

public class Koth {

	private int id;
	private String name;
	private Region region;

	private int secondsRemaining;
	
	private boolean active;

	private int cap;
	private Player capPlayer;

	public Koth(int i, String n, Region region) {
		setId(i);
		setName(n);
		setRegion(region);
		setCap(0);
		setCapPlayer(null);
		setActive(false);
	}

	public void pulse(ArrayList<Player> p) {
		if (isActive()) {
			
			if (getSecondsRemaining() == 0) {
				setCap(0);
				setSecondsRemaining(0);
				setActive(false);
				setCapPlayer(null);
				return;
			}
			
			if (p != null) {
				if (p.contains(getCapPlayer())) {
					setCap(cap += 1);

					if (getCap() == 100) {
						// end koth
						setCap(0);
						rewardPlayer(getCapPlayer());
						
						//after
						setSecondsRemaining(0);
						setActive(false);
						setCapPlayer(null);
					}
				}
			}
			setSecondsRemaining(secondsRemaining -=1);
		}
	}

	private void rewardPlayer(Player p) {
		FPlayer fp = FPlayers.getInstance().getByPlayer(p);
		Faction f = fp.getFaction();
		
		if (f != null && !f.isWilderness() && !f.isSafeZone() && !f.isWarZone()) {
			//reward faction
		}
		
		//reward player
	}
	
	public void start() {
		P.p.km.getActiveKothThread().newKoth(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Player getCapPlayer() {
		return capPlayer;
	}

	public void setCapPlayer(Player capPlayer) {
		this.capPlayer = capPlayer;
	}

	public int getSecondsRemaining() {
		return secondsRemaining;
	}

	public int setSecondsRemaining(int secondsRemaining) {
		this.secondsRemaining = secondsRemaining;
		return secondsRemaining;
	}

	public int getCap() {
		return cap;
	}

	public int setCap(int cap) {
		this.cap = cap;
		return cap;
	}

}
