package rezscripts.core.factions.zcore.persist;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import rezscripts.core.factions.FPlayer;
import rezscripts.core.factions.FPlayers;
import rezscripts.core.factions.Factions;
import rezscripts.core.factions.P;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public abstract class MemoryFPlayers extends FPlayers {
    public Map<String, FPlayer> fPlayers = new ConcurrentSkipListMap<String, FPlayer>(String.CASE_INSENSITIVE_ORDER);

    public void clean() {
        for (FPlayer fplayer : this.fPlayers.values()) {
            if (!Factions.getInstance().isValidFactionId(fplayer.getFactionId())) {
                P.p.log("Reset faction data (invalid faction:" + fplayer.getFactionId() + ") for player " + fplayer.getName());
                fplayer.resetFactionData(false);
            }
        }
    }

    public Collection<FPlayer> getOnlinePlayers() {
        Set<FPlayer> entities = new HashSet<FPlayer>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            entities.add(this.getByPlayer(player));
        }
        return entities;
    }

    @Override
    public FPlayer getByPlayer(Player player) {
        return getById(player.getUniqueId().toString());
    }

    @Override
    public List<FPlayer> getAllFPlayers() {
        return new ArrayList<FPlayer>(fPlayers.values());
    }

    @Override
    public abstract void forceSave();

    public abstract void load();

    @Override
    public FPlayer getByOfflinePlayer(OfflinePlayer player) {
        return getById(player.getUniqueId().toString());
    }

    @Override
    public FPlayer getById(String id) {
        FPlayer player = fPlayers.get(id);
        if (player == null) {
            player = generateFPlayer(id);
        }
        return player;
    }

    public abstract FPlayer generateFPlayer(String id);

    public abstract void convertFrom(MemoryFPlayers old);
}
