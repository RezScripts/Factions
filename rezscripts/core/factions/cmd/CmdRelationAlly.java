package rezscripts.core.factions.cmd;

import rezscripts.core.factions.struct.Relation;

public class CmdRelationAlly extends FRelationCommand {

    public CmdRelationAlly() {
        aliases.add("ally");
        targetRelation = Relation.ALLY;
    }
}
