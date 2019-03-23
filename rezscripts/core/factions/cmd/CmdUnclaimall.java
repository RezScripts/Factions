package rezscripts.core.factions.cmd;

import org.bukkit.Bukkit;

import rezscripts.core.factions.Board;
import rezscripts.core.factions.Conf;
import rezscripts.core.factions.P;
import rezscripts.core.factions.event.LandUnclaimAllEvent;
import rezscripts.core.factions.integration.Econ;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

public class CmdUnclaimall extends FCommand {

    public CmdUnclaimall() {
        this.aliases.add("unclaimall");
        this.aliases.add("declaimall");

        //this.requiredArgs.add("");
        //this.optionalArgs.put("", "");

        this.permission = Permission.UNCLAIM_ALL.node;
        this.disableOnLock = true;

        senderMustBePlayer = true;
        senderMustBeMember = false;
        senderMustBeModerator = true;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        if (Econ.shouldBeUsed()) {
            double refund = Econ.calculateTotalLandRefund(myFaction.getLandRounded());
            if (Conf.bankEnabled && Conf.bankFactionPaysLandCosts) {
                if (!Econ.modifyMoney(myFaction, refund, TL.COMMAND_UNCLAIMALL_TOUNCLAIM.toString(), TL.COMMAND_UNCLAIMALL_FORUNCLAIM.toString())) {
                    return;
                }
            } else {
                if (!Econ.modifyMoney(fme, refund, TL.COMMAND_UNCLAIMALL_TOUNCLAIM.toString(), TL.COMMAND_UNCLAIMALL_FORUNCLAIM.toString())) {
                    return;
                }
            }
        }

        LandUnclaimAllEvent unclaimAllEvent = new LandUnclaimAllEvent(myFaction, fme);
        Bukkit.getServer().getPluginManager().callEvent(unclaimAllEvent);
        if (unclaimAllEvent.isCancelled()) {
            return;
        }

        Board.getInstance().unclaimAll(myFaction.getId());
        myFaction.msg(TL.COMMAND_UNCLAIMALL_UNCLAIMED, fme.describeTo(myFaction, true));

        if (Conf.logLandUnclaims) {
            P.p.log(TL.COMMAND_UNCLAIMALL_LOG.format(fme.getName(), myFaction.getTag()));
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_UNCLAIMALL_DESCRIPTION;
    }

}
