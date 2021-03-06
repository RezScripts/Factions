package rezscripts.core.factions.scoreboards;

import java.util.List;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.P;
import rezscripts.core.factions.zcore.util.TL;
import rezscripts.core.factions.zcore.util.TagUtil;

public abstract class FSidebarProvider {

    public abstract String getTitle(FPlayer fplayer);

    public abstract List<String> getLines(FPlayer fplayer);

    public String replaceTags(FPlayer fPlayer, String s) {
        return qualityAssure(TagUtil.parsePlain(fPlayer, s));
    }

    public String replaceTags(Faction faction, FPlayer fPlayer, String s) {
        return qualityAssure(TagUtil.parsePlain(faction, fPlayer, s));
    }

    private String qualityAssure(String line) {
        if (line.contains("{notFrozen}") || line.contains("{notPermanent}")) {
            return "n/a"; // we dont support support these error variables in scoreboards
        }
        if (line.contains("{ig}")) {
            // since you can't really fit a whole "Faction Home: world, x, y, z" on one line
            // we assume it's broken up into two lines, so returning our tl will suffice.
            return TL.COMMAND_SHOW_NOHOME.toString();
        }
        return P.p.txt.parse(line); // finally add color :)
    }
}