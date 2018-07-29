package org.ships.block.blockhandler.types;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class Fire implements BlockHandler<BlockState> {
	Block block;
	BlockData data;

	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void saveBlockData() {
		this.data = this.block.getBlockData();
	}

	@Override
	public void applyBlockData() {
		this.block.setBlockData(this.data);
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.ATTACHABLE;
	}

	@Override
	public boolean isReady() {
		return true;
	}
}
