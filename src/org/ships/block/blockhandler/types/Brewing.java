package org.ships.block.blockhandler.types;

import org.bukkit.block.BrewingStand;
import org.ships.block.blockhandler.Container;
import org.ships.plugin.BlockHandlerNotReady;

public class Brewing extends Container<BrewingStand> {
	int brewingTime;
	int level;

	@Override
	public void apply(BrewingStand stand) {
		super.apply(stand);
		this.applyTime(stand);
		this.applyFuelLevel(stand);
	}

	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof BrewingStand;
	}

	@Override
	public void save(boolean forRemoval) throws BlockHandlerNotReady {
		this.saveTime();
		this.saveFuelLevel();
		super.save(forRemoval);
	}

	public void applyTime(BrewingStand stand) {
		stand.setBrewingTime(this.brewingTime);
	}

	public void saveTime() {
		this.brewingTime = this.getState().getBrewingTime();
	}

	public void applyFuelLevel(BrewingStand stand) {
		stand.setFuelLevel(this.level);
	}

	public void saveFuelLevel() {
		this.level = this.getState().getFuelLevel();
	}
}
