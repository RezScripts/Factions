package rezscripts.core.factions.cmd;

import org.bukkit.Bukkit;

import rezscripts.core.factions.*;
import rezscripts.core.factions.event.FactionRenameEvent;
import rezscripts.core.factions.scoreboards.FTeamWrapper;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.util.MiscUtil;
import rezscripts.core.factions.zcore.util.TL;

import java.util.ArrayList;

public class CmdTag extends FCommand {

    public CmdTag() {
        this.aliases.add("tag");
        this.aliases.add("rename");

        this.requiredArgs.add("faction tag");
        //this.optionalArgs.put("", "");

        this.permission = Permission.TAG.node;
        this.disableOnLock = true;

        senderMustBePlayer = true;
        senderMustBeMember = false;
        senderMustBeModerator = true;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        String tag = this.argAsString(0);

        // TODO does not first test cover selfcase?
        if (Factions.getInstance().isTagTaken(tag) && !MiscUtil.getComparisonString(tag).equals(myFaction.getComparisonTag())) {
            msg(TL.COMMAND_TAG_TAKEN);
            return;
        }

        ArrayList<String> errors = MiscUtil.validateTag(tag);
        if (errors.size() > 0) {
            sendMessage(errors);
            return;
        }

        // if economy is enabled, they're not on the bypass list, and this command has a cost set, make sure they can pay
        if (!canAffordCommand(Conf.econCostTag, TL.COMMAND_TAG_TOCHANGE.toString())) {
            return;
        }

        // trigger the faction rename event (cancellable)
        FactionRenameEvent renameEvent = new FactionRenameEvent(fme, tag);
        Bukkit.getServer().getPluginManager().callEvent(renameEvent);
        if (renameEvent.isCancelled()) {
            return;
        }

        // then make 'em pay (if applicable)
        if (!payForCommand(Conf.econCostTag, TL.COMMAND_TAG_TOCHANGE, TL.COMMAND_TAG_FORCHANGE)) {
            return;
        }

        String oldtag = myFaction.getTag();
        myFaction.setTag(tag);

        // Inform
        for (FPlayer fplayer : FPlayers.getInstance().getOnlinePlayers()) {
            if (fplayer.getFactionId().equals(myFaction.getId())) {
                fplayer.msg(TL.COMMAND_TAG_FACTION, fme.describeTo(myFaction, true), myFaction.getTag(myFaction));
                continue;
            }

            // Broadcast the tag change (if applicable)
            if (Conf.broadcastTagChanges) {
                Faction faction = fplayer.getFaction();
                fplayer.msg(TL.COMMAND_TAG_CHANGED, fme.getColorTo(faction) + oldtag, myFaction.getTag(faction));
            }
        }

        FTeamWrapper.updatePrefixes(myFaction);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_TAG_DESCRIPTION;
    }

}
