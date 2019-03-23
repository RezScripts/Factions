package rezscripts.core.factions.cmd;

import mkremins.fanciful.FancyMessage;
import rezscripts.core.factions.Conf;
import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.FPlayers;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

import org.bukkit.ChatColor;

public class CmdShowInvites extends FCommand {

    public CmdShowInvites() {
        super();
        aliases.add("showinvites");
        permission = Permission.SHOW_INVITES.node;

        senderMustBePlayer = true;
        senderMustBeMember = true;
    }

    @Override
    public void perform() {
        FancyMessage msg = new FancyMessage(TL.COMMAND_SHOWINVITES_PENDING.toString()).color(ChatColor.GOLD);
        for (String id : myFaction.getInvites()) {
            FPlayer fp = FPlayers.getInstance().getById(id);
            String name = fp != null ? fp.getName() : id;
            msg.then(name + " ").color(ChatColor.WHITE).tooltip(TL.COMMAND_SHOWINVITES_CLICKTOREVOKE.format(name)).command("/" + Conf.baseCommandAliases.get(0) + " deinvite " + name);
        }

        sendFancyMessage(msg);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_SHOWINVITES_DESCRIPTION;
    }


}
