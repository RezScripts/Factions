package rezscripts.core.factions.cmd;

import org.bukkit.entity.Player;

import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.upgrade.UpgradeManager;
import rezscripts.core.factions.util.manager.ManagerInstances;
import rezscripts.core.factions.zcore.CommandVisibility;
import rezscripts.core.factions.zcore.util.TL;

public class CmdGive extends FCommand {

    public CmdGive() {
        super();
        this.aliases.add("give");
        this.aliases.add("item");

        this.requiredArgs.add("player name");
        this.requiredArgs.add("item ID");
        this.requiredArgs.add("amount");
        //this.optionalArgs.put("", "");

        this.permission = Permission.GIVE.node;
        this.disableOnLock = false;

        this.visibility = CommandVisibility.SECRET;
        
        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
    	Player p = this.argAsBestPlayerMatch(0);
    	
    	if (p == null) {
    		this.sender.sendMessage("player not found");
    		return;
    	}
    	
    	int item = 1;
    	
    	try {
    		item = this.argAsInt(1);
    	}catch(Exception e) {
    		this.sender.sendMessage("Invalid id. Defaulting to 0");
    		item = 1;
    	}
    	
    	int amount = 1;
    	
    	try {
    		amount = this.argAsInt(2);
    	}catch(Exception e) {
    		this.sender.sendMessage("Invalid amount. Defaulting to 1");
    		amount = 1;
    	}
    	
    	switch(item) {
    		case 1:
    			for (int i = 0; i < amount; i++) {
    			p.getInventory().addItem(ManagerInstances.getInstance(UpgradeManager.class).getUpgradeToken());
    			}
    		p.sendMessage("Recieved: " + amount + "x Faction Upgrade Tokens");
    		return;
    	}
    }

	@Override
	public TL getUsageTranslation() {
        return TL.COMMAND_GIVE_DESCRIPTION;
	}

}
