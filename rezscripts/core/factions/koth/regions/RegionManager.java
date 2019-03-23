package rezscripts.core.factions.koth.regions;


import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import rezscripts.core.factions.P;
import rezscripts.core.factions.util.manager.AbstractManagerCore;

public class RegionManager extends AbstractManagerCore {

    protected static final String GENERAL_NAME = "Wilderness";
    protected static final RegionPoint MIN_POINT = new RegionPoint( -5000, -5000, -5000 );
    protected static final RegionPoint MAX_POINT = new RegionPoint( 5000, 5000, 5000 );
    protected static ArrayList<Player> beingChecked;
    public static ArrayList<Region> regions;

    public RegionManager(P plugin) {
        super( plugin );
    }

    public static void reload() {
        regions.clear();
        for (World w : plugin.getServer().getWorlds()) {
            Region r = new Region( GENERAL_NAME, w, Arrays.asList( MIN_POINT, MAX_POINT ) );
            regions.add( r );
        }
    }


    public static ArrayList<Player> getBeingChecked() {
        return beingChecked;
    }

    public static void setBeingChecked(ArrayList<Player> beingChecked) {
        RegionManager.beingChecked = beingChecked;
    }

    public static Region getRegion(Player p) {
        return getRegion( p.getLocation() );
    }

    public static Region getRegion(Location loc) {
        Region curr = null;
        for (Region r : regions) {
            if (r.isWithin( loc )) {
                if (curr == null || r.size < curr.size)
                    curr = r;
            }
        }
        if (curr == null) {
            // This should only happen if a world is loaded manually after regions have loaded
            Region r = new Region( GENERAL_NAME, loc.getWorld(), Arrays.asList( MIN_POINT, MAX_POINT ) );
            regions.add( r );
            return r;
        }
        return curr;
    }

    @Override
    public void initialize() {
        regions = new ArrayList<Region>();
        reload();
    }

}
