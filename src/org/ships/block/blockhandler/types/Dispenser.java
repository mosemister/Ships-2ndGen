package org.ships.block.blockhandler.types;

import org.ships.block.blockhandler.Container;

public class Dispenser extends Container<org.bukkit.block.Dispenser> {
	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof org.bukkit.block.Dispenser;
	}
}
