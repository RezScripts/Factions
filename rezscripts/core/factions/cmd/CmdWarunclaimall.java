package rezscripts.core.factions.cmd;

import rezscripts.core.factions.Board;
import rezscripts.core.factions.Conf;
import rezscripts.core.factions.Factions;
import rezscripts.core.factions.P;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

public class CmdWarunclaimall extends FCommand {

    public CmdWarunclaimall() {
        this.aliases.add("warunclaimall");
        this.aliases.add("wardeclaimall");

        //this.requiredArgs.add("");
        //this.optionalArgs.put("", "");

        this.permission = Permission.MANAGE_WAR_ZONE.node;
        this.disableOnLock = true;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        Board.getInstance().unclaimAll(Factions.getInstance().getWarZone().getId());
        msg(TL.COMMAND_WARUNCLAIMALL_SUCCESS);

        if (Conf.logLandUnclaims) {
            P.p.log(TL.COMMAND_WARUNCLAIMALL_LOG.format(fme.getName()));
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_WARUNCLAIMALL_DESCRIPTION;
    }

}
