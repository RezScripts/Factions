package rezscripts.core.factions.spawners;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import rezscripts.core.factions.Board;
import rezscripts.core.factions.FLocation;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.P;
import rezscripts.core.factions.util.manager.AbstractManagerCore;

public class SpawnerManager{

    private int mobStackRadius;
//    private Set<EntityType> entitiesToStack;
    private ArrayList<LivingEntity> validEnity = new ArrayList<>();
    private ArrayList<LivingEntity> entitiesToMultiplyOnDeath = new ArrayList<>();
    
    private ArrayList<String> instantKillPlayers = new ArrayList<>();

    
	public SpawnerManager(int mobStackRadius) {
        this.mobStackRadius = mobStackRadius;
        startEntityClock();
	}

    private void startEntityClock(){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(P.p, new Runnable() {
            public void run() {
                // Iterate through all worlds
                for (World world : Bukkit.getServer().getWorlds()) {
                    // Iterate through all entities in this world (if not disabled)
                    for (LivingEntity entity : world.getLivingEntities()) {
                        if(!checkEntity(entity)) continue;
                        // Iterate through all entities in range
                        for (Entity nearby : entity.getNearbyEntities(mobStackRadius, mobStackRadius, mobStackRadius)) {

                            if(checkEntity(nearby)) {
                                P.p.getStackEntity().stack(entity, (LivingEntity) nearby);
                            }
                        }
                    }
                }

            }
        }, 20L, 10L);

    }
    
    public ArrayList<LivingEntity> getEntitiesToMultiplyOnDeath() {
        return entitiesToMultiplyOnDeath;
    }

    private boolean checkEntity(Entity entity){
        if(!(entity instanceof LivingEntity)){
            return false;
        }
        if(!entity.isValid()){
            return false;
        }
        if (entity.getType() == EntityType.PLAYER) {
            return false;
        }
        
//        if(!entitiesToStack.contains(entity.getType())){
//            return false;
//        }

        FLocation floc = new FLocation(entity.getLocation());
        Faction f = Board.getInstance().getFactionAt(floc);
        
        if (f.isSafeZone() || f.isWarZone()) {
        	return false;
        }
        
//        if(entity.getType() == EntityType.SLIME){
//            return false;
//
//        }
        return true;
    }
    

    public ArrayList<LivingEntity> getValidEntity() {
        return this.validEnity;
    }

    public ArrayList<String> getInstantKillPlayers() {
        return instantKillPlayers;
    }
}
