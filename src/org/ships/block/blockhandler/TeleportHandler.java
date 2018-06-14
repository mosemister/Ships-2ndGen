package org.ships.block.blockhandler;

import org.bukkit.block.BlockState;

public interface TeleportHandler<T extends BlockState> extends BlockHandler<T> {
	
	public void applyEndLocation();
	public void saveEndLocation();
	public void applyExactTeleport();
	public void saveExactTeleport();

}
