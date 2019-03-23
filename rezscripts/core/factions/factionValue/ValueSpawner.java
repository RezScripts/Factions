package rezscripts.core.factions.factionValue;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ValueSpawner {

	private String identifier;
	private Material mat;
	private EntityType data;
	private double value;
	
	public ValueSpawner(Material m, EntityType e, double val) {
		setMat(m);
		setData(e);
		setValue(val);
	}

	public Material getMat() {
		return mat;
	}

	public void setMat(Material mat) {
		this.mat = mat;
	}

	public EntityType getData() {
		return data;
	}

	public void setData(EntityType data) {
		this.data = data;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
}
