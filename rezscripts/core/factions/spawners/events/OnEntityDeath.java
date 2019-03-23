package rezscripts.core.factions.spawners.events;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import rezscripts.core.factions.Board;
import rezscripts.core.factions.FLocation;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.P;
import rezscripts.core.factions.upgrade.UpgradeManager;
import rezscripts.core.factions.util.manager.ManagerInstances;

public class OnEntityDeath implements Listener{

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)) {
            return; // Not a living entity.
        }

        LivingEntity entity =  e.getEntity();

        if(entity.getType() == EntityType.ARMOR_STAND){
            return;
        }


        if (entity.getType() != EntityType.PLAYER) {
        	
            //TODO: drop multiply
			Faction f = Board.getInstance().getFactionAt(new FLocation(entity.getLocation()));
			if (f != null && !f.isWarZone() && !f.isSafeZone() && !f.isWilderness()) {
				
				double multiplyUpgrade = (ManagerInstances.getInstance(UpgradeManager.class)
						.getUpgradeById(2).getMultiplier())*f.getUpgrades().get(2);				
//				P.p.log("Items: " + e.getDrops().size() + " | Multiply: +" + multiplyUpgrade 
//						+ "%  |  Result: Extra " 
//						+ e.getDrops().size()*(multiplyUpgrade/100)
//						+ " items");
				
				if (!e.getDrops().isEmpty()) {
				int extra = (int) Math.round(e.getDrops().size()*(multiplyUpgrade/100));
				
				
				if(extra < 1) { extra = 1;}
				
//				P.p.log(extra + " + " + e.getDrops().get(0).getType().toString() + " dropped");
				
				ItemStack extraDrop = e.getDrops().get(0);
				extraDrop.setAmount((int) extra);
				
				e.getEntity().getWorld().dropItemNaturally(e.getEntity().getEyeLocation()
						, extraDrop);
				}
			}
        	
            if(P.p.getEntityStacker().getEntitiesToMultiplyOnDeath().contains(entity) || (entity.getKiller() != null && P.p.getEntityStacker().getInstantKillPlayers().contains(entity.getKiller().getName()))){
                P.p.getEntityStacker().getEntitiesToMultiplyOnDeath().remove(entity);
                
//                Faction f = Board.getInstance().getFactionAt(new FLocation(entity.getLocation()));
//            	P.p.log("stack kill");
//            	
//                if (f != null && !f.isWarZone() && !f.isSafeZone() && !f.isWilderness()) {
//                	double multiplyUpgrade = f.getUpgrades().get(2);
//                	P.p.log(multiplyUpgrade);
//                }
                
                e.setDroppedExp(e.getDroppedExp() * multiplyDropsReturnExp(entity,e.getDrops()));
                return;

            }
            P.p.getStackEntity().attemptUnstackOne(entity);
        }

//        if(P.p.getMobStackerConfig().stackOnlySpawnerMobs){
//            P.p.getEntityStacker().getValidEntity().remove(entity);
//        }
    }


    public int multiplyDropsReturnExp(LivingEntity dead, List<ItemStack> drops){
        int amountToMultiply = P.p.getStackEntity().parseAmount(dead.getCustomName());
        if(amountToMultiply <=1) return 1;
        for(ItemStack i : drops){
            ItemStack item = new ItemStack(i);
            item.setAmount(amountToMultiply);
            dead.getWorld().dropItemNaturally(dead.getEyeLocation(),item);

        }
        return amountToMultiply;

    }

}