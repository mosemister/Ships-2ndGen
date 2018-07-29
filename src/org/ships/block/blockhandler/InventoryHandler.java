package org.ships.block.blockhandler;

import org.bukkit.block.Container;
import org.ships.plugin.BlockHandlerNotReady;

public interface InventoryHandler<T extends Container> extends BlockHandler<T> {
	@Override
	public default boolean isReady() {
		return this.getBlock().getState() instanceof Container;
	}

	@Override
	public default void save(boolean forRemoval) throws BlockHandlerNotReady {
		System.out.println("\t InventoryHandler -> OnSave -> ForRemoval: " + forRemoval);
		this.saveBlockData();
		this.saveInventory();
		if (forRemoval) {
			this.getState().getInventory().clear();
		}
	}

	@Override
	public default void apply(T blockstate) {
		this.applyInventory(blockstate);
	}

	public void saveInventory();

	public void applyInventory(T var1);

	@Override
	public default BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}
}
