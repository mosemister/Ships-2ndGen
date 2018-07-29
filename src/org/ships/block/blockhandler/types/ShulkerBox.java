package org.ships.block.blockhandler.types;

import org.ships.block.blockhandler.Container;

public class ShulkerBox extends Container<org.bukkit.block.ShulkerBox> {
	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof org.bukkit.block.ShulkerBox;
	}
}
