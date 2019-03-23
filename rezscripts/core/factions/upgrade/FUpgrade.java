package rezscripts.core.factions.upgrade;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

public class FUpgrade {
	
	private int id;
	private String Name;
	private int maxUpgrade;
	private Material showMaterial;
	private HashMap<Integer, Integer> costList;
	private ArrayList<String> description;
	private double multiplier;
	
	public FUpgrade(int i, String name, int max,double multi, Material display, ArrayList<String> desc, HashMap<Integer, Integer> costs) {
		setId(i);
		setName(name);
		setMaxUpgrade(max);
		setCostList(costs);
		setShowMaterial(display);
		setDescription(desc);
		setMultiplier(multi);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public int getMaxUpgrade() {
		return maxUpgrade;
	}


	public void setMaxUpgrade(int maxUpgrade) {
		this.maxUpgrade = maxUpgrade;
	}


	public HashMap<Integer, Integer> getCostList() {
		return costList;
	}


	public void setCostList(HashMap<Integer, Integer> costList) {
		this.costList = costList;
	}


	public Material getShowMaterial() {
		return showMaterial;
	}


	public void setShowMaterial(Material showMaterial) {
		this.showMaterial = showMaterial;
	}


	public ArrayList<String> getDescription() {
		return description;
	}


	public void setDescription(ArrayList<String> description) {
		this.description = description;
	}


	public double getMultiplier() {
		return multiplier;
	}


	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
}
