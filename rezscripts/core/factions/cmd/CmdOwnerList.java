package rezscripts.core.factions.cmd;

import rezscripts.core.factions.Board;
import rezscripts.core.factions.Conf;
import rezscripts.core.factions.FLocation;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;


public class CmdOwnerList extends FCommand {

    public CmdOwnerList() {
        super();
        this.aliases.add("ownerlist");

        //this.requiredArgs.add("");
        //this.optionalArgs.put("", "");

        this.permission = Permission.OWNERLIST.node;
        this.disableOnLock = false;

        senderMustBePlayer = true;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        boolean hasBypass = fme.isAdminBypassing();

        if (!hasBypass && !assertHasFaction()) {
            return;
        }

        if (!Conf.ownedAreasEnabled) {
            fme.msg(TL.COMMAND_OWNERLIST_DISABLED);
            return;
        }

        FLocation flocation = new FLocation(fme);

        if (Board.getInstance().getFactionAt(flocation) != myFaction) {
            if (!hasBypass) {
                fme.msg(TL.COMMAND_OWNERLIST_WRONGFACTION);
                return;
            }
            //TODO: This code won't ever be called.
            myFaction = Board.getInstance().getFactionAt(flocation);
            if (!myFaction.isNormal()) {
                fme.msg(TL.COMMAND_OWNERLIST_NOTCLAIMED);
                return;
            }
        }

        String owners = myFaction.getOwnerListString(flocation);

        if (owners == null || owners.isEmpty()) {
            fme.msg(TL.COMMAND_OWNERLIST_NONE);
            return;
        }

        fme.msg(TL.COMMAND_OWNERLIST_OWNERS, owners);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_OWNERLIST_DESCRIPTION;
    }
}
