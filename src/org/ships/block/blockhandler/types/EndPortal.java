package org.ships.block.blockhandler.types;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
import org.bukkit.block.data.BlockData;
import org.ships.block.blockhandler.BlockPriority;
import org.ships.block.blockhandler.TeleportHandler;

public class EndPortal implements TeleportHandler<EndGateway> {
	Block block;
	BlockData data;
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
	public boolean isReady() {
		return this.getBlock().getState() instanceof EndGateway;
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}

	@Override
	public void saveBlockData() {
		this.data = this.getBlock().getBlockData();
	}

	@Override
	public void applyBlockData() {
		this.getBlock().setBlockData(this.data);
	}

	@Override
	public void applyEndLocation(EndGateway gateway) {
		gateway.setExitLocation(this.loc);
	}

	@Override
	public void saveEndLocation() {
		this.loc = this.getState().getExitLocation();
	}

	@Override
	public void applyExactTeleport(EndGateway gateway) {
		gateway.setExactTeleport(this.exact);
	}

	@Override
	public void saveExactTeleport() {
		this.exact = this.getState().isExactTeleport();
	}
}
