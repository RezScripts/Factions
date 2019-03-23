package rezscripts.core.factions.util.manager;


import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import rezscripts.core.factions.P;

public abstract class AbstractManager implements Listener {

    public AbstractManager(P plugin) {
        try {
            load(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Little bit intensive but only used on startup
        String full = this.getClass().toString();
        String s = full;
        try {
            full = full.substring(full.indexOf("me.customRPG.") + "me.customRPG.".length());
            String[] data = full.split("\\.");
            s = data[0] + "-" + data[data.length - 1];
        } catch (Exception e) {
            System.out.println("Error parsing AbstractManager display name for " + this.getClass());
        }
        System.out.println("Loaded " + s + ".");
    }

    private void load(P plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        initialize();
        try {
            ManagerInstances.registerManager(this.getClass(), this, plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void unload(P plugin) {
        HandlerList.unregisterAll(this);
    }

    public abstract void initialize();

}
