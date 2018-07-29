package org.ships.block.blockhandler.types;

import org.ships.block.blockhandler.Container;

public class Dropper extends Container<org.bukkit.block.Dropper> {
	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof org.bukkit.block.Dropper;
	}
}
