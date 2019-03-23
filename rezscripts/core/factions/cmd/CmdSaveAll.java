package rezscripts.core.factions.cmd;

import rezscripts.core.factions.Board;
import rezscripts.core.factions.Conf;
import rezscripts.core.factions.FPlayers;
import rezscripts.core.factions.Factions;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

public class CmdSaveAll extends FCommand {

    public CmdSaveAll() {
        super();
        this.aliases.add("saveall");
        this.aliases.add("save");

        //this.requiredArgs.add("");
        //this.optionalArgs.put("", "");

        this.permission = Permission.SAVE.node;
        this.disableOnLock = false;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        FPlayers.getInstance().forceSave(false);
        Factions.getInstance().forceSave(false);
        Board.getInstance().forceSave(false);
        Conf.save();
        msg(TL.COMMAND_SAVEALL_SUCCESS);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_SAVEALL_DESCRIPTION;
    }

}