package rezscripts.core.factions.cmd;

import rezscripts.core.factions.struct.Relation;

public class CmdRelationEnemy extends FRelationCommand {

    public CmdRelationEnemy() {
        aliases.add("enemy");
        targetRelation = Relation.ENEMY;
    }
}
