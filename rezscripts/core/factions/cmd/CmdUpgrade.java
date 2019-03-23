package rezscripts.core.factions.cmd;

import org.bukkit.entity.Player;

import rezscripts.core.factions.Faction;
import rezscripts.core.factions.menus.FupgradeMenu;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

public class CmdUpgrade extends FCommand {

    public CmdUpgrade() {
        super();
        this.aliases.add("upgrade");
        this.aliases.add("up");
        this.aliases.add("upgrades");

        this.permission = Permission.UPGRADE.node;
        this.disableOnLock = false;

        senderMustBePlayer = true;
        senderMustBeMember = true;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        Faction faction = myFaction;
        if (this.argIsSet(0)) {
            faction = this.argAsFaction(0);
        }
        if (faction == null) {
            return;
        }
        
    	FupgradeMenu.openMenu((Player) this.sender, myFaction, 1);
    }

	@Override
	public TL getUsageTranslation() {
        return TL.COMMAND_UPGRADE_DESCRIPTION;
	}

}
