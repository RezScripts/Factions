package rezscripts.core.factions.spawners.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

import rezscripts.core.factions.P;

public class OnEntityDespawn implements Listener{

    @EventHandler
    public void onDespawnEvent(ItemDespawnEvent e){
        if(e.getEntity() instanceof LivingEntity){
            if (P.p.getEntityStacker().getValidEntity().contains(e.getEntity())) {
                P.p.getEntityStacker().getValidEntity().remove(e.getEntity());
            }
        }
    }
}