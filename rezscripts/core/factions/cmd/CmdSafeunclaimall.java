package rezscripts.core.factions.cmd;

import rezscripts.core.factions.Board;
import rezscripts.core.factions.Conf;
import rezscripts.core.factions.Factions;
import rezscripts.core.factions.P;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

public class CmdSafeunclaimall extends FCommand {

    public CmdSafeunclaimall() {
        this.aliases.add("safeunclaimall");
        this.aliases.add("safedeclaimall");

        //this.requiredArgs.add("");
        //this.optionalArgs.put("radius", "0");

        this.permission = Permission.MANAGE_SAFE_ZONE.node;
        this.disableOnLock = true;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;

    }

    @Override
    public void perform() {
        Board.getInstance().unclaimAll(Factions.getInstance().getSafeZone().getId());
        msg(TL.COMMAND_SAFEUNCLAIMALL_UNCLAIMED);

        if (Conf.logLandUnclaims) {
            P.p.log(TL.COMMAND_SAFEUNCLAIMALL_UNCLAIMEDLOG.format(sender.getName()));
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_SAFEUNCLAIMALL_DESCRIPTION;
    }

}
