package rezscripts.core.factions.cmd;

import org.bukkit.command.ConsoleCommandSender;

import rezscripts.core.factions.Conf;
import rezscripts.core.factions.Conf.Backend;
import rezscripts.core.factions.zcore.persist.json.FactionsJSON;
import rezscripts.core.factions.zcore.util.TL;

public class CmdConvert extends FCommand {

    public CmdConvert() {
        this.aliases.add("convert");

        this.requiredArgs.add("[MYSQL|JSON]");
    }

    @Override
    public void perform() {
        if (!(this.sender instanceof ConsoleCommandSender)) {
            this.sender.sendMessage(TL.GENERIC_CONSOLEONLY.toString());
            return;
        }
        Backend nb = Backend.valueOf(this.argAsString(0).toUpperCase());
        if (nb == Conf.backEnd) {
            this.sender.sendMessage(TL.COMMAND_CONVERT_BACKEND_RUNNING.toString());
            return;
        }
        switch (nb) {
            case JSON:
                FactionsJSON.convertTo();
                break;
            default:
                this.sender.sendMessage(TL.COMMAND_CONVERT_BACKEND_INVALID.toString());
                return;

        }
        Conf.backEnd = nb;
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_CONVERT_DESCRIPTION;
    }

}
