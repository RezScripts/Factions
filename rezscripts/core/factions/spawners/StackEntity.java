package rezscripts.core.factions.spawners;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;

import rezscripts.core.factions.P;

public class StackEntity {


    public StackEntity(){
    }

    /** The stacked mob custom name format. */
    public static int INVALID_STACK = -1;

    /*
     * Methods used to Stack or Unstack mobs
     */
    public boolean attemptUnstackOne(LivingEntity livingEntity) {

        String displayName = livingEntity.getCustomName();
        int mobsAmount = parseAmount(displayName);

        // Kill this mob
        livingEntity.setHealth(0);

        if (mobsAmount <= 1) {
            // The stack is down to one mob; don't recreate it
            return false;
        }


        // Recreate the stack with one less mob
        mobsAmount--;
        String mobDisplayName = ChatColor.translateAlternateColorCodes('&', "&a%number%&2x&a " + livingEntity.getType().getName()).replace("%number%",String.valueOf(mobsAmount));

        LivingEntity dupEntity = (LivingEntity) livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), livingEntity.getType());
        dupEntity.setCustomName(mobDisplayName.replace("%type%", livingEntity.getType().name().toLowerCase().substring(0,1).toUpperCase()+ livingEntity.getType().name().substring(1).toLowerCase()));
        dupEntity.setCustomNameVisible(true);

        return true;
    }

    public boolean unstackAll(LivingEntity livingEntity){
        livingEntity.setCustomName("1 mob");
        //Hide name from users
        livingEntity.setCustomNameVisible(false);
        livingEntity.setHealth(0);
        P.p.getEntityStacker().getValidEntity().remove(livingEntity);
        return true;
    }


    public boolean stack(LivingEntity target, LivingEntity stackee) {
        if (target.getType() != stackee.getType()) {
            return false; // The entities must be of the same type.
        }

        String displayName = target.getCustomName();
        int alreadyStacked = parseAmount(displayName);
        int stackedMobsAlready = 1;

        // Check if the stackee is already a stack
        if (isStacked(stackee)) {
            stackedMobsAlready = parseAmount(stackee.getCustomName());
        }
        if(stackedMobsAlready >= 500 || alreadyStacked >= 500) return false;
        stackee.remove();
        P.p.getEntityStacker().getValidEntity().remove(stackee);
        if (alreadyStacked == INVALID_STACK) {
            // The target is NOT a stack

            String newDisplayName = ChatColor.translateAlternateColorCodes('&', "&a%number%&2x&a " + target.getType().getName()).replace("%number%", String.valueOf(stackedMobsAlready + 1));
            target.setCustomName(newDisplayName.replace("%type%", target.getType().name().toLowerCase().substring(0,1).toUpperCase()+ target.getType().name().substring(1).toLowerCase()));
            target.setCustomNameVisible(true);
        } else {
            // The target is already a stack
            String newDisplayName = ChatColor.translateAlternateColorCodes('&', "&a%number%&2x&a " + target.getType().getName()).replace("%number%", String.valueOf(alreadyStacked + stackedMobsAlready));
            target.setCustomName(newDisplayName.replace("%type%", target.getType().name().toLowerCase().substring(0,1).toUpperCase()+ target.getType().name().substring(1).toLowerCase()));
        }
        return true;
    }

    /*
     * "Helper" methods
     */
    public int parseAmount(String displayName) {
        if (displayName == null) {
            return INVALID_STACK; // No display name, therefor not a stack.
        }


          String colourStrip = ChatColor.stripColor(displayName);
         String str = colourStrip.replaceAll("[^-?0-9]+", " ");

        try {
            return Integer.valueOf(str.replaceAll(" ",""));
        }catch(NumberFormatException e){
            return INVALID_STACK;
        }
    }

    private boolean isStacked(LivingEntity entity) {
        return parseAmount(entity.getCustomName()) != INVALID_STACK;
    }



}