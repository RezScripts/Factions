package rezscripts.core.factions.util.manager;

import rezscripts.core.factions.P;

public abstract class AbstractManagerCore extends AbstractManager {

    public static P plugin;

    public AbstractManagerCore(P pl) {
        super(pl);
        plugin = pl;
    }

}