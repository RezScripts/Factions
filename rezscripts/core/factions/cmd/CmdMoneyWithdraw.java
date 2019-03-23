package rezscripts.core.factions.cmd;

import org.bukkit.ChatColor;

import rezscripts.core.factions.Conf;
import rezscripts.core.factions.P;
import rezscripts.core.factions.iface.EconomyParticipator;
import rezscripts.core.factions.integration.Econ;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;


public class CmdMoneyWithdraw extends FCommand {

    public CmdMoneyWithdraw() {
        this.aliases.add("w");
        this.aliases.add("withdraw");

        this.requiredArgs.add("amount");
        this.optionalArgs.put("faction", "yours");

        this.permission = Permission.MONEY_WITHDRAW.node;

        senderMustBePlayer = true;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        double amount = this.argAsDouble(0, 0d);
        EconomyParticipator faction = this.argAsFaction(1, myFaction);
        if (faction == null) {
            return;
        }
        boolean success = Econ.transferMoney(fme, faction, fme, amount);

        if (success && Conf.logMoneyTransactions) {
            P.p.log(ChatColor.stripColor(P.p.txt.parse(TL.COMMAND_MONEYWITHDRAW_WITHDRAW.toString(), fme.getName(), Econ.moneyString(amount), faction.describeTo(null))));
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MONEYWITHDRAW_DESCRIPTION;
    }
}
