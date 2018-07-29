package org.ships.block.blockhandler.types;

import org.bukkit.inventory.FurnaceInventory;
import org.ships.block.blockhandler.Container;
import org.ships.plugin.BlockHandlerNotReady;

public class Furnace extends Container<org.bukkit.block.Furnace> {
	short burnTime;

	@Override
	public void apply(org.bukkit.block.Furnace blockstate) {
		this.applyBurnTime(blockstate);
		super.apply(blockstate);
	}

	@Override
	public void applyInventory(org.bukkit.block.Furnace furnace) {
		FurnaceInventory snapInventory = (FurnaceInventory) this.inventory;
		FurnaceInventory conInventory = this.getState().getInventory();
		conInventory.setFuel(snapInventory.getFuel());
	}

	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof org.bukkit.block.Furnace;
	}

	@Override
	public void save(boolean forRemoval) throws BlockHandlerNotReady {
		this.saveBurnTime();
		super.save(forRemoval);
	}

	public void applyBurnTime(org.bukkit.block.Furnace blockstate) {
		blockstate.setBurnTime(this.burnTime);
	}

	public void saveBurnTime() {
		this.burnTime = this.getState().getBurnTime();
	}
}
