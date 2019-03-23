package rezscripts.core.factions.zcore.persist;

import rezscripts.core.factions.Board;
import rezscripts.core.factions.FPlayers;
import rezscripts.core.factions.Factions;
import rezscripts.core.factions.zcore.MPlugin;

public class SaveTask implements Runnable {

    private static boolean running = false;

    MPlugin p;

    public SaveTask(MPlugin p) {
        this.p = p;
    }

    public void run() {
        if (!p.getAutoSave() || running) {
            return;
        }
        running = true;
        p.preAutoSave();
        Factions.getInstance().forceSave(false);
        FPlayers.getInstance().forceSave(false);
        Board.getInstance().forceSave(false);
        p.postAutoSave();
        running = false;
    }
}
