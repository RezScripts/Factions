package rezscripts.core.factions.cmd;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

public class CmdAnnounce extends FCommand {

    public CmdAnnounce() {
        super();
        this.aliases.add("ann");
        this.aliases.add("announce");

        this.requiredArgs.add("message");
        this.errorOnToManyArgs = false;

        this.permission = Permission.ANNOUNCE.node;
        this.disableOnLock = false;

        senderMustBePlayer = true;
        senderMustBeMember = true;
        senderMustBeModerator = true;
    }

    @Override
    public void perform() {
        String prefix = ChatColor.GREEN + myFaction.getTag() + ChatColor.YELLOW + " [" + ChatColor.GRAY + me.getName() + ChatColor.YELLOW + "] " + ChatColor.RESET;
        String message = StringUtils.join(args, " ");

        for (Player player : myFaction.getOnlinePlayers()) {
            player.sendMessage(prefix + message);
        }

        // Add for offline players.
        for (FPlayer fp : myFaction.getFPlayersWhereOnline(false)) {
            myFaction.addAnnouncement(fp, prefix + message);
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_ANNOUNCE_DESCRIPTION;
    }

}
