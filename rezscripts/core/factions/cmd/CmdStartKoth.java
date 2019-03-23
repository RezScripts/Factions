package rezscripts.core.factions.cmd;

import rezscripts.core.factions.P;
import rezscripts.core.factions.koth.Koth;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.CommandVisibility;
import rezscripts.core.factions.zcore.util.TL;

public class CmdStartKoth extends FCommand{

	public CmdStartKoth() {
		super();
		
		this.aliases.add("startkoth");
		this.aliases.add("kothstart");
		
		this.requiredArgs.add("id");
		
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
    	if (!this.argIsSet(0)) {
    		this.me.sendMessage("Usage: /command (koth id)");
    		return;
    	}
    	
    	Koth k = P.p.km.getKothById(this.argAsInt(0));
    	if (k == null) {
    		this.me.sendMessage("Bruh, that koth does not exist!");
    		return;
    	}
    	
    	k.start();
    }

	@Override
	public TL getUsageTranslation() {
		return null;
	}
}
