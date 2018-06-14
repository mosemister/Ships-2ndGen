package org.ships.block.blockhandler.types;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.ships.block.blockhandler.BlockPriority;
import org.ships.block.blockhandler.TeleportHandler;

public class EndPortal implements TeleportHandler<org.bukkit.block.EndGateway>{

	Block block;
	Location loc;
	boolean exact;
	
	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void remove(Material material) {
		saveEndLocation();
		this.block.setType(material);
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}

	@Override
	public void apply() {
		applyEndLocation();
	}

	@Override
	public void applyEndLocation() {
		getState().setExitLocation(this.loc);
	}

	@Override
	public void saveEndLocation() {
		this.loc = getState().getExitLocation();
	} 

	@Override
	public void applyExactTeleport() {
		getState().setExactTeleport(this.exact);
	}
	
	@Override
	public void saveExactTeleport() {
		this.exact = getState().isExactTeleport();
	}

}
