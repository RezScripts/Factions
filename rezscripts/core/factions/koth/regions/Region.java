package rezscripts.core.factions.koth.regions;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.managers.RegionManager;

import rezscripts.core.factions.P;


public class Region {

    public String name;
    //    public int x1, x2, y1, y2, z1, z2;
    public RegionBoundary boundary;
    public World world;
    public double size;

    public long startTime = 3000, endTime = 9000, timeDiff = 6000;
    public int cycleLengthSeconds = 300; // 0 for no cycle
    private long cycleStart = System.currentTimeMillis();

    private ArrayList<String> welcomeMsgs;

    private String compactMsg = "";

    public Region(String name, World world, List<RegionPoint> arr) {
        this.name = name;
        this.world = world;
        this.welcomeMsgs = new ArrayList<String>();
        boundary = RegionBoundary.create( arr );
        this.size = boundary.area();
        welcomeMsgs.add( "" );
    }

    public void sendWelcome(Player p, Region last) {
//        if (this.name.equals( RegionManager.GENERAL_NAME )) {
//            return;
//        }
    }

    public long getTime() {
        if (cycleLengthSeconds == 0 || timeDiff == 0 || startTime == endTime)
            return startTime;
        long sec = (System.currentTimeMillis() - cycleStart) / 1000;
        sec %= cycleLengthSeconds * 2;
        long finalTime = startTime;
        if (sec <= cycleLengthSeconds)
            finalTime = startTime + timeDiff * sec / cycleLengthSeconds;
        else
            finalTime = endTime - timeDiff * (sec - cycleLengthSeconds) / cycleLengthSeconds;
        return finalTime;
    }

    public boolean isWithin(Player p) {
        return isWithin( p.getLocation() );
    }

    public boolean isWithin(Location loc) {
        if (!loc.getWorld().equals( this.world ))
            return false;

        return boundary.contains( loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() );
        //        double x = (int) loc.getX();
        //        double y = (int) loc.getY();
        //        double z = (int) loc.getZ();
        //        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

	public boolean isWithinRegion(Player p) {
		return isWithin(p.getLocation());
	}

}
