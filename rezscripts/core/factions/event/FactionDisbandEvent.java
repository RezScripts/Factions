package rezscripts.core.factions.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.FPlayers;
import rezscripts.core.factions.Factions;

/**
 * Event called when a faction is disbanded.
 */
public class FactionDisbandEvent extends FactionEvent implements Cancellable {

    private boolean cancelled = false;
    private Player sender;

    public FactionDisbandEvent(Player sender, String factionId) {
        super(Factions.getInstance().getFactionById(factionId));
        this.sender = sender;
    }

    public FPlayer getFPlayer() {
        return FPlayers.getInstance().getByPlayer(sender);
    }

    public Player getPlayer() {
        return sender;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean c) {
        cancelled = c;
    }
}
