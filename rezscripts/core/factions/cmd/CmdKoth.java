package rezscripts.core.factions.cmd;

import rezscripts.core.factions.P;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.CommandVisibility;
import rezscripts.core.factions.zcore.util.TL;

public class CmdKoth extends FCommand {

	public CmdKoth() {
		super();
		this.aliases.add("koth");

		this.requiredArgs.add("start/end");
		this.requiredArgs.add("Koth ID");
		// this.optionalArgs.put("", "");

		this.permission = Permission.KOTH.node;
		this.disableOnLock = false;

		this.visibility = CommandVisibility.SECRET;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
    public void perform() {
    	if (this.args.get(0).equalsIgnoreCase("start")){
    		if (P.p.km.getActiveKothThread().activeKoth == null) {
    			int id = 0;
    			try {
    				id = this.argAsInt(1);
    			}catch(Exception e) {
    				id = 0;
    			}
    			
    			P.p.km.getActiveKothThread().activeKoth = P.p.km.getKothById(id);
    			this.sender.sendMessage("Koth started");
    			return;
    		}else {
    			this.sender.sendMessage("Koth already started. Use /f koth stop 0 to end it");
    			return;
    		}
    	}
    	
    	if (this.args.get(0).equalsIgnoreCase("stop")){
    		if (P.p.km.getActiveKothThread().activeKoth == null) {
    			return;
    		}
    		P.p.km.getActiveKothThread().activeKoth.setSecondsRemaining(1);
    	}
    	
    	return;
    }

	@Override
	public TL getUsageTranslation() {
		return TL.COMMAND_GIVE_DESCRIPTION;
	}

}
