package rezscripts.core.factions.factionValue;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import rezscripts.core.factions.Faction;
import rezscripts.core.factions.P;

public class ChunkValueThread extends Thread {

	public Faction fac;
	public ArrayList<Chunk> chunks;
	public boolean checked;

	public ChunkValueThread(Faction f, ArrayList<Chunk> c) {
		fac = f;
		chunks = c;
		checked = false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		// check chunk value

		if (checked) {
			this.stop();
		}

		if (chunks == null) {
			this.stop();
		}

		if (fac == null) {
			this.stop();
		}

		ValueManager instanceMan = P.vm;

		double total = 0;

		try {
			for (Chunk c : chunks) {

				if (c.getWorld() == null) {
					continue;
				}
				
				if (!c.isLoaded()) {
					c.load();
				}

				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						for (int y = 0; y < 256; y++) {

							Material material = c.getBlock((c.getX() * 16) + x, y, (c.getZ() * 16) + z).getType();

							if (material == null) {
								continue;
							}

							// check the block value
							
							Block b = null;
							
							try {
								b = c.getBlock(x, y, z);
							}catch(Exception e) {}
							
							if (b == null) {
								continue;
							}
							
							switch (material) {
							case MOB_SPAWNER:
								try {
								if (b instanceof CreatureSpawner) {
									CreatureSpawner spawner = (CreatureSpawner) b
											.getState();
									EntityType spawnedType = spawner.getSpawnedType();
									if (spawnedType != null) {
										if (instanceMan.getValueSpawner(spawnedType) != null) {
											ValueSpawner s = instanceMan.getValueSpawner(spawnedType);
											total = total + s.getValue();
										}
									}
								}
								break;
								}catch(Exception e) {
									break;
								}
							default:
								// block
								if (instanceMan.getValueBlock(material) != null) {
									ValueBlock s = instanceMan.getValueBlock(material);
									total = total + s.getValue();
								}
								break;
							}
						}
					}
				}
			}
			checked = true;
			fac.setLandValue(total);
		} catch (Exception e) {
//			P.p.log("Error loading value of faction: " + fac.getTag() + "... Trying again");
			new ChunkValueThread(fac, chunks).start();
			this.stop();
		}
	}

}
