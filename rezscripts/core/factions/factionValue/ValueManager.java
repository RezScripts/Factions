package rezscripts.core.factions.factionValue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import rezscripts.core.factions.FLocation;
import rezscripts.core.factions.Faction;
import rezscripts.core.factions.P;
import rezscripts.core.factions.util.manager.AbstractManagerCore;
import rezscripts.core.factions.zcore.persist.MemoryFactions;

public class ValueManager extends AbstractManagerCore {

	public ArrayList<ValueBlock> valueBlocks;
	public ArrayList<ValueSpawner> valueSpawners;

	public ArrayList<Faction> trackedFactions;

	public ValueManager(P pl) {
		super(pl);
	}

	@SuppressWarnings("deprecation")
	public void checkTick() {
		for (Faction f : MemoryFactions.getInstance().getAllFactions()) {
			if (f.isSafeZone() || f.isWarZone() || f.isWilderness()) {
				continue;
			}

			if (!trackedFactions.contains(f)) {
				trackedFactions.add(f);
				P.p.getServer().getScheduler().runTaskTimerAsynchronously(P.p, new BukkitRunnable() {
					@Override
					public void run() {
						try {
							updateWorth(f);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, 20L, 40L);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void initialize() {
		trackedFactions = new ArrayList<Faction>();
		valueBlocks = new ArrayList<ValueBlock>();
		valueSpawners = new ArrayList<ValueSpawner>();
		
		try {
			readConfig();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

		P.p.getServer().getScheduler().runTaskTimer(P.p, new BukkitRunnable() {
			@Override
			public void run() {
				checkTick();
			}
		}, 20L, 40L);
		//TODO: how often to check value of faction land
	}

	@SuppressWarnings("deprecation")
	private void readConfig() throws IOException, InvalidConfigurationException {
		File f = new File(P.p.getDataFolder() + "/values.YAML");

		if (!f.exists()) {
			f.createNewFile();
			makeNewConfig(f);
		}

		YamlConfiguration conf = new YamlConfiguration();
		conf.load(f);
		
		int i = 1;
		ConfigurationSection blocks = conf.getConfigurationSection("Blocks");

		if (blocks != null) {
			while (blocks.getConfigurationSection("Block " + i) != null) {
				ConfigurationSection sec = blocks.getConfigurationSection("Block " + i);
				Material mat = Material.getMaterial(sec.getInt("Material ID"));
				short dat = (short) sec.getInt("Data");
				double val = sec.getDouble("Value");
				P.p.log("Value Block added: " + mat.toString());
				valueBlocks.add(new ValueBlock(mat, dat, val));
				i++;
			}
		}

		i = 1;
		ConfigurationSection spawners = conf.getConfigurationSection("Spawners");
		if (spawners != null) {
			while (spawners.getConfigurationSection("Spawners " + i) != null) {
				ConfigurationSection sec = spawners.getConfigurationSection("Spawners " + i);
				Material mat = Material.getMaterial(sec.getInt("Material ID"));
				EntityType dat = EntityType.valueOf(sec.getString("Entity Type"));
				double val = sec.getDouble("Value");
				valueSpawners.add(new ValueSpawner(mat, dat, val));
				P.p.log("Value Spawner added: " + dat.toString());
				i++;
			}
		}
		P.p.log("Faction Values loaded with: " + valueBlocks.size() + valueSpawners.size() + " items.");
	}

	@SuppressWarnings("deprecation")
	private void makeNewConfig(File f) {

		valueBlocks.add(new ValueBlock(Material.BEACON, (short) 0, 1000));

		YamlConfiguration conf = new YamlConfiguration();
		try {
			conf.load(f);
			conf.createSection("Blocks");
			ConfigurationSection blocks = conf.getConfigurationSection("Blocks");
			int i = 1;
			for (ValueBlock item : valueBlocks) {
				blocks.createSection("Block " + i);
				ConfigurationSection sec = blocks.getConfigurationSection("Block " + i);
				sec.set("Material ID", item.getMat().getId());
				sec.set("Data", item.getData());
				sec.set("Value", item.getValue());
				i++;
			}

			conf.createSection("Spawners");
			ConfigurationSection spawners = conf.getConfigurationSection("Spawners");
			i = 1;
			for (EntityType t : EntityType.values()) {
				if (t.isAlive() && !t.equals(EntityType.ARMOR_STAND)) {
					spawners.createSection("Spawner " + i);
					ConfigurationSection sec = spawners.getConfigurationSection("Spawner " + i);
					sec.set("Entity Type", t.toString());
					sec.set("Value", 0);
					i++;
				}
			}
			conf.save(f);

		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void updateWorth(Faction faction) {
		// // Do nothing if this area should not be calculated.
		try {
			if (faction.isSafeZone() || faction.isWarZone() || faction.isWilderness()) {
				return;
			}

			ArrayList<Chunk> chunks = new ArrayList<Chunk>();

			if (faction.getAllClaims().isEmpty()) {
				return;
			}

			for (FLocation l : faction.getAllClaims()) {

				if (l.getLocation() == null) {
					continue;
				}

				if (l.getWorld() == null) {
					continue;
				}

				if (l.getWorld().getChunkAt(l.getLocation()) == null) {
					continue;
				}
												
				Chunk chunk = l.getWorld().getChunkAt(l.getLocation());

				if (chunks.contains(chunk)) {
					continue;
				}

				chunks.add(chunk);
			}
			
			new ChunkValueThread(faction, chunks).run();

		} catch (Exception e) {
			return;
		}
	}

	public ValueBlock getValueBlock(Material mat) {
		for (ValueBlock vs : valueBlocks) {
			if (vs.getMat().equals(mat)) {
				return vs;
			}
		}
		return null;
	}

	public ValueSpawner getValueSpawner(EntityType spawnedType) {
		for (ValueSpawner vs : valueSpawners) {
			if (vs.getData().equals(spawnedType)) {
				return vs;
			}
		}
		return null;
	}

}
