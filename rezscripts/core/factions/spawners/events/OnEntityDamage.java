package rezscripts.core.factions.spawners.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import rezscripts.core.factions.P;

public class OnEntityDamage implements Listener{

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player) && e.getEntity() instanceof LivingEntity){
                if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                    if(e.getEntity().getFallDistance() > 15 && e.getFinalDamage() >= 20){
//                        if(P.p.getMobStackerConfig().mobsToStack.contains(e.getEntityType()) && P.p.getMobStackerConfig().killMobStackOnFall)
//                        P.p.getEntityStacker().getEntitiesToMultiplyOnDeath().add((LivingEntity) e.getEntity());
                    }

            }
            //Check if cause is Fall Damage
            //If so remove stack
        }
    }
}