package rezscripts.core.factions.cmd;

import rezscripts.core.factions.Conf;
import rezscripts.core.factions.struct.ChatMode;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

public class CmdChat extends FCommand {

	public CmdChat() {
		super();
		this.aliases.add("c");
		this.aliases.add("chat");

		// this.requiredArgs.add("");
		this.optionalArgs.put("mode", "next");

		this.permission = Permission.CHAT.node;
		this.disableOnLock = false;

		senderMustBePlayer = true;
		senderMustBeMember = true;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		if (!Conf.factionOnlyChat) {
			msg(TL.COMMAND_CHAT_DISABLED.toString());
			return;
		}

		String modeString = this.argAsString(0);
		ChatMode modeTarget = fme.getChatMode().getNext();

		if (modeString != null) {
			modeString = modeString.toLowerCase();
			if (modeString.startsWith("p")) {
				modeTarget = ChatMode.PUBLIC;
			} else if (modeString.startsWith("a")) {
				modeTarget = ChatMode.ALLIANCE;
			} else if (modeString.startsWith("f")) {
				modeTarget = ChatMode.FACTION;
			} else if (modeString.startsWith("t")) {
				modeTarget = ChatMode.TRUCE;
			} else if (modeString.startsWith("e")) {
				modeTarget = ChatMode.ENEMY;
			} else {
				msg(TL.COMMAND_CHAT_INVALIDMODE);
				return;
			}
		}

		fme.setChatMode(modeTarget);

		if (fme.getChatMode() == ChatMode.PUBLIC)

		{
			msg(TL.COMMAND_CHAT_MODE_PUBLIC);
		} else if (fme.getChatMode() == ChatMode.ALLIANCE) {
			msg(TL.COMMAND_CHAT_MODE_ALLIANCE);
		} else if (fme.getChatMode() == ChatMode.TRUCE) {
			msg(TL.COMMAND_CHAT_MODE_TRUCE);
		} 
		else if (fme.getChatMode() == ChatMode.ENEMY) {
			msg(TL.COMMAND_CHAT_MODE_ENEMY);
		} else {
			msg(TL.COMMAND_CHAT_MODE_FACTION);
		}
	}

	@Override
	public TL getUsageTranslation() {
		return TL.COMMAND_CHAT_DESCRIPTION;
	}
}
