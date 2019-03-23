package rezscripts.core.factions.cmd;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.P;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.struct.Relation;
import rezscripts.core.factions.util.LazyLocation;
import rezscripts.core.factions.zcore.util.TL;

public class CmdSetFWarp extends FCommand {

    public CmdSetFWarp() {
        super();
        this.aliases.add("setwarp");
        this.aliases.add("sw");
        this.requiredArgs.add("warp name");
        this.senderMustBeMember = true;
        this.senderMustBeModerator = true;
        this.senderMustBePlayer = true;
        this.permission = Permission.SETWARP.node;
    }

    @Override
    public void perform() {
        if (!(fme.getRelationToLocation() == Relation.MEMBER)) {
            fme.msg(TL.COMMAND_SETFWARP_NOTCLAIMED);
            return;
        }

        int maxWarps = P.p.getConfig().getInt("max-warps", 5);
        if (maxWarps <= myFaction.getWarps().size()) {
            fme.msg(TL.COMMAND_SETFWARP_LIMIT, maxWarps);
            return;
        }

        if (!transact(fme)) {
            return;
        }

        String warp = argAsString(0);
        LazyLocation loc = new LazyLocation(fme.getPlayer().getLocation());
        myFaction.setWarp(warp, loc);
        fme.msg(TL.COMMAND_SETFWARP_SET, warp);
    }

    private boolean transact(FPlayer player) {
        return !P.p.getConfig().getBoolean("warp-cost.enabled", false) || player.isAdminBypassing() || payForCommand(P.p.getConfig().getDouble("warp-cost.setwarp", 5), TL.COMMAND_SETFWARP_TOSET.toString(), TL.COMMAND_SETFWARP_FORSET.toString());
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_SETFWARP_DESCRIPTION;
    }
}
