package rezscripts.core.factions.cmd;

import rezscripts.core.factions.Conf;
import rezscripts.core.factions.P;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

public class CmdReload extends FCommand {

    public CmdReload() {
        super();
        this.aliases.add("reload");

        //this.requiredArgs.add("");
        this.optionalArgs.put("file", "all");

        this.permission = Permission.RELOAD.node;
        this.disableOnLock = false;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        long timeInitStart = System.currentTimeMillis();
        Conf.load();
        P.p.reloadConfig();
        P.p.loadLang();
        long timeReload = (System.currentTimeMillis() - timeInitStart);

        msg(TL.COMMAND_RELOAD_TIME, timeReload);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_RELOAD_DESCRIPTION;
    }
}
