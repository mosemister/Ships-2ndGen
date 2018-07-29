package org.ships.block.blockhandler.types;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class Head implements BlockHandler<Skull> {
	protected Block block;
	protected BlockData data;
	protected OfflinePlayer owner;

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
		return this.getBlock().getState() instanceof Skull;
	}

	@Override
	public void save(boolean forRemoval) {
		this.saveOwner();
		this.saveBlockData();
	}

	@Override
	public void apply(Skull blockstate) {
		this.applyOwner(blockstate);
	}

	public void saveOwner() {
		this.owner = this.getState().getOwningPlayer();
	}

	public void applyOwner(Skull blockstate) {
		blockstate.setOwningPlayer(this.owner);
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.ATTACHABLE;
	}

	@Override
	public void saveBlockData() {
		this.data = this.getBlock().getBlockData();
	}

	@Override
	public void applyBlockData() {
		this.getBlock().setBlockData(this.data);
	}
}
