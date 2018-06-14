package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.ships.block.blockhandler.Container;

public class Furnace extends Container<org.bukkit.block.Furnace>{
	
	short burnTime;
	
	@Override
	public void apply() {
		applyBurnTime();
		super.apply();
	}
	
	@Override
	public void remove(Material material) {
		saveBurnTime();
		super.remove(material);
	}
	
	public void applyBurnTime() {
		getState().setBurnTime(this.burnTime);
	}
	
	public void saveBurnTime() {
		this.burnTime = getState().getBurnTime();
	}

}
