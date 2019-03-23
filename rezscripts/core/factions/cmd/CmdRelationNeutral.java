package rezscripts.core.factions.cmd;

import rezscripts.core.factions.struct.Relation;

public class CmdRelationNeutral extends FRelationCommand {

    public CmdRelationNeutral() {
        aliases.add("neutral");
        targetRelation = Relation.NEUTRAL;
    }
}
