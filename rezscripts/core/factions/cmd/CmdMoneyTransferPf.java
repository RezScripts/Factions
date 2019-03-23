package rezscripts.core.factions.cmd;

import org.bukkit.ChatColor;

import rezscripts.core.factions.Conf;
import rezscripts.core.factions.P;
import rezscripts.core.factions.iface.EconomyParticipator;
import rezscripts.core.factions.integration.Econ;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;


public class CmdMoneyTransferPf extends FCommand {

    public CmdMoneyTransferPf() {
        this.aliases.add("pf");

        this.requiredArgs.add("amount");
        this.requiredArgs.add("player");
        this.requiredArgs.add("faction");

        //this.optionalArgs.put("", "");

        this.permission = Permission.MONEY_P2F.node;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        double amount = this.argAsDouble(0, 0d);
        EconomyParticipator from = this.argAsBestFPlayerMatch(1);
        if (from == null) {
            return;
        }
        EconomyParticipator to = this.argAsFaction(2);
        if (to == null) {
            return;
        }

        boolean success = Econ.transferMoney(fme, from, to, amount);

        if (success && Conf.logMoneyTransactions) {
            P.p.log(ChatColor.stripColor(P.p.txt.parse(TL.COMMAND_MONEYTRANSFERPF_TRANSFER.toString(), fme.getName(), Econ.moneyString(amount), from.describeTo(null), to.describeTo(null))));
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MONEYTRANSFERPF_DESCRIPTION;
    }
}
