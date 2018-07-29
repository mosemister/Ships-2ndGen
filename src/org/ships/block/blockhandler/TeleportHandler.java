package org.ships.block.blockhandler;

import org.bukkit.block.BlockState;

public interface TeleportHandler<T extends BlockState> extends BlockHandler<T> {
	public void applyEndLocation(T var1);

	public void saveEndLocation();

	public void applyExactTeleport(T var1);

	public void saveExactTeleport();

	@Override
	public default void save(boolean forRemoval) {
		this.saveBlockData();
		this.saveExactTeleport();
		this.saveEndLocation();
	}

	@Override
	public default void apply(T blockstate) {
		this.applyEndLocation(blockstate);
		this.applyExactTeleport(blockstate);
	}
}
