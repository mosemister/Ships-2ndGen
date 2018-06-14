package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.ships.block.blockhandler.Container;

public class Brewing extends Container<BrewingStand> {
	
	int brewingTime;
	int level;
	
	@Override
	public void apply() {
		applyTime();
		applyFuelLevel();
		super.apply();
	}
	
	@Override
	public void remove(Material material) {
		saveTime();
		saveFuelLevel();
		super.remove(material);
	}
	
	public void applyTime() {
		this.getState().setBrewingTime(this.brewingTime);
	}
	
	public void saveTime() {
		this.brewingTime = this.getState().getBrewingTime();
	}
	
	public void applyFuelLevel() {
		this.getState().setFuelLevel(this.level);
	}
	
	public void saveFuelLevel() {
		this.level = this.getState().getFuelLevel();
	}

}
