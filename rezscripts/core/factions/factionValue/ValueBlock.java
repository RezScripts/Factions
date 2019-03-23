package rezscripts.core.factions.factionValue;

import org.bukkit.Material;

public class ValueBlock {

	private Material mat;
	private short data;
	private double value;
	
	public ValueBlock(Material m, short dat, double val) {
		setMat(m);
		setData(dat);
		setValue(val);
	}

	public Material getMat() {
		return mat;
	}

	public void setMat(Material mat) {
		this.mat = mat;
	}

	public short getData() {
		return data;
	}

	public void setData(short data) {
		this.data = data;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
