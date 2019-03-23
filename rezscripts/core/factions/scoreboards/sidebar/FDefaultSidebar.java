package rezscripts.core.factions.scoreboards.sidebar;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.P;
import rezscripts.core.factions.scoreboards.FSidebarProvider;

public class FDefaultSidebar extends FSidebarProvider {

    @Override
    public String getTitle(FPlayer fplayer) {
        return replaceTags(fplayer, P.p.getConfig().getString("scoreboard.default-title", "{name}"));
    }

    @Override
    public List<String> getLines(FPlayer fplayer) {
        if (fplayer.hasFaction()) {
            return getOutput(fplayer, "scoreboard.default");
        } else if (P.p.getConfig().getBoolean("scoreboard.factionless-enabled", false)) {
            return getOutput(fplayer, "scoreboard.factionless");
        }
        return getOutput(fplayer, "scoreboard.default"); // no faction, factionless-board disabled
    }

    public List<String> getOutput(FPlayer fplayer, String list) {
        List<String> lines = P.p.getConfig().getStringList(list);

        if (lines == null || lines.isEmpty()) {
            return new ArrayList<String>();
        }

        ListIterator<String> it = lines.listIterator();
        while (it.hasNext()) {
            it.set(replaceTags(fplayer, it.next()));
        }
        return lines;
    }
}