package org.ships.block.blockhandler.types;

import org.ships.block.blockhandler.Container;

public class Hopper extends Container<org.bukkit.block.Hopper> {
	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof org.bukkit.block.Hopper;
	}
}
