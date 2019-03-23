package rezscripts.core.factions.scoreboards.sidebar;

import java.util.List;
import java.util.ListIterator;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.P;
import rezscripts.core.factions.scoreboards.FSidebarProvider;

public class FInfoSidebar extends FSidebarProvider {
    private final Faction faction;

    public FInfoSidebar(Faction faction) {
        this.faction = faction;
    }

    @Override
    public String getTitle(FPlayer fplayer) {
        return faction.getRelationTo(fplayer).getColor() + faction.getTag();
    }

    @Override
    public List<String> getLines(FPlayer fplayer) {
        List<String> lines = P.p.getConfig().getStringList("scoreboard.finfo");

        ListIterator<String> it = lines.listIterator();
        while (it.hasNext()) {
            it.set(replaceTags(faction, fplayer, it.next()));
        }
        return lines;
    }
}