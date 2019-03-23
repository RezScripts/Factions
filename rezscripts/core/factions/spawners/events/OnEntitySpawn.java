package rezscripts.core.factions.spawners.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import rezscripts.core.factions.P;

public class OnEntitySpawn implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
                P.p.getEntityStacker().getValidEntity().add(e.getEntity());
        
        }
        if(e.getEntity().getType().equals(EntityType.SLIME)){
            Slime slime = (Slime) e.getEntity();
            //valid display name
            slime.setSize(3);

//            if(P.p.getStackEntity().parseAmount(slime.getCustomName()) != -1){
//                slime.setSize(4);
//            }
        }
    }
}