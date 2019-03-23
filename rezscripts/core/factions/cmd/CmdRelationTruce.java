package rezscripts.core.factions.cmd;

import rezscripts.core.factions.struct.Relation;

public class CmdRelationTruce extends FRelationCommand {

    public CmdRelationTruce() {
        aliases.add("truce");
        targetRelation = Relation.TRUCE;
    }
}