package rezscripts.core.factions.spawners.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SlimeSplitEvent;

public class OnSlimeSplit implements Listener{

    @EventHandler
    public void onSplit(SlimeSplitEvent e){
    	e.setCancelled(true);
    	e.getEntity().damage(e.getEntity().getHealth()+10000);
    }
}