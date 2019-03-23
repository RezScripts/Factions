package rezscripts.core.factions.koth;

import java.util.ArrayList;

import rezscripts.core.factions.P;
import rezscripts.core.factions.koth.regions.Region;
import rezscripts.core.factions.koth.regions.RegionManager;
import rezscripts.core.factions.koth.regions.RegionPoint;
import rezscripts.core.factions.util.manager.ManagerInstances;

public class KothManager{

	ArrayList<Koth> kothList;
	private KothPulse activeKothThread;
	
	@SuppressWarnings("deprecation")
	public KothManager() {
		kothList = new ArrayList<Koth>();
		
		//    public Region(String name, World world, List<RegionPoint> arr) {

		kothList.add(new Koth(0, "Obsidian Realm", 
				new Region("Obby Koth", P.p.getServer().getWorld("world"), 
						new ArrayList<RegionPoint>() {
							private static final long serialVersionUID = 1L;
						{
							add(new RegionPoint(230, 80, 48));
							add(new RegionPoint(238, 88, 40));
						}}
				)));
		
		
		for (Koth k : kothList) {
			ManagerInstances.getInstance(RegionManager.class).regions.add(k.getRegion());
		}
		
		setActiveKothThread(new KothPulse());
		
		P.p.getServer().getPluginManager().registerEvents(getActiveKothThread(), P.p);
		P.p.getServer().getScheduler().runTaskTimerAsynchronously(P.p,
				getActiveKothThread(), 20L, 20L);
	}
	
	
	public Koth getKothById(int id) {
		for (Koth k : kothList) {
			if (k.getId() == id) {
				return k;
			}
		}
		return null;
	}


	public KothPulse getActiveKothThread() {
		return activeKothThread;
	}


	public void setActiveKothThread(KothPulse activeKothThread) {
		this.activeKothThread = activeKothThread;
	}
	
	
}
