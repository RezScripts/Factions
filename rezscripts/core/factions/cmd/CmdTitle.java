package rezscripts.core.factions.cmd;

import rezscripts.core.factions.Conf;
import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;
import rezscripts.core.factions.zcore.util.TextUtil;

public class CmdTitle extends FCommand {

    public CmdTitle() {
        this.aliases.add("title");

        this.requiredArgs.add("player name");
        this.optionalArgs.put("title", "");

        this.permission = Permission.TITLE.node;
        this.disableOnLock = true;

        senderMustBePlayer = true;
        senderMustBeMember = false;
        senderMustBeModerator = true;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        FPlayer you = this.argAsBestFPlayerMatch(0);
        if (you == null) {
            return;
        }

        args.remove(0);
        String title = TextUtil.implode(args, " ");

        if (!canIAdministerYou(fme, you)) {
            return;
        }

        // if economy is enabled, they're not on the bypass list, and this command has a cost set, make 'em pay
        if (!payForCommand(Conf.econCostTitle, TL.COMMAND_TITLE_TOCHANGE, TL.COMMAND_TITLE_FORCHANGE)) {
            return;
        }

        you.setTitle(title);

        // Inform
        myFaction.msg(TL.COMMAND_TITLE_CHANGED, fme.describeTo(myFaction, true), you.describeTo(myFaction, true));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_TITLE_DESCRIPTION;
    }

}
