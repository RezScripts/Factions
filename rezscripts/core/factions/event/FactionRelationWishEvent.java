package rezscripts.core.factions.event;

import org.bukkit.event.Cancellable;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.struct.Relation;

public class FactionRelationWishEvent extends FactionPlayerEvent implements Cancellable {
    private final Faction targetFaction;
    private final Relation currentRelation;
    private final Relation targetRelation;

    private boolean cancelled;

    public FactionRelationWishEvent(FPlayer caller, Faction sender, Faction targetFaction, Relation currentRelation, Relation targetRelation) {
        super(sender, caller);

        this.targetFaction = targetFaction;
        this.currentRelation = currentRelation;
        this.targetRelation = targetRelation;
    }

    public Faction getTargetFaction() {
        return targetFaction;
    }

    public Relation getCurrentRelation() {
        return currentRelation;
    }

    public Relation getTargetRelation() {
        return targetRelation;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
