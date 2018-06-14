package org.ships.block.blockhandler;

import org.bukkit.block.Container;

public interface InventoryHandler<T extends Container> extends BlockHandler<T> {
	
	public void saveInventory();
	public void applyInventory();
	
	@Override
	public default BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}


}
