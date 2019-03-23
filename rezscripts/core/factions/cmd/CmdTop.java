package rezscripts.core.factions.cmd;

import org.bukkit.entity.Player;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.Factions;
import rezscripts.core.factions.integration.Econ;
import rezscripts.core.factions.menus.FtopMenu;
import rezscripts.core.factions.struct.Permission;
import rezscripts.core.factions.zcore.util.TL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CmdTop extends FCommand {

    public CmdTop() {
        super();
        this.aliases.add("top");
        this.aliases.add("t");

        //this.requiredArgs.add("");
        this.optionalArgs.put("page", "1");

        this.permission = Permission.TOP.node;
        this.disableOnLock = false;

        senderMustBePlayer = true;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        // Can sort by: money, members, online, allies, enemies, power, land.
        // Get all Factions and remove non player ones.
        ArrayList<Faction> factionList = Factions.getInstance().getAllFactions();
        factionList.remove(Factions.getInstance().getWilderness());
        factionList.remove(Factions.getInstance().getSafeZone());
        factionList.remove(Factions.getInstance().getWarZone());

        String criteria = "value";

        if (criteria.equalsIgnoreCase("value")) {
            Collections.sort(factionList, new Comparator<Faction>() {
                @Override
                public int compare(Faction f1, Faction f2) {
                  double f1Size = Econ.calculateTotalValue(f1);
                  double f2Size = Econ.calculateTotalValue(f2);
                  
                    if (f1Size < f2Size) {
                        return 1;
                    } else if (f1Size > f2Size) {
                        return -1;
                    }
                    return 0;
                }
            });
        }

        final int pageheight = 10;
        int pagenumber = this.argAsInt(1, 1);
        int pagecount = (factionList.size() / pageheight) + 1;
        if (pagenumber > pagecount) {
            pagenumber = pagecount;
        } else if (pagenumber < 1) {
            pagenumber = 1;
        }
        int start = (pagenumber - 1) * pageheight;
        int end = start + pageheight;
        if (end > factionList.size()) {
            end = factionList.size();
        }

        FtopMenu.showMenu((Player) this.sender, factionList.subList(start, end), Integer.parseInt(optionalArgs.get("page")));
    }

    @Override	
    public TL getUsageTranslation() {
        return TL.COMMAND_TOP_DESCRIPTION;
    }
}
