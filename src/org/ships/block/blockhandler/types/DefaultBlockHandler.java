package org.ships.block.blockhandler.types;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.BlockData;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class DefaultBlockHandler implements BlockHandler<BlockState> {
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
		this.data = this.getBlock().getBlockData();
	}

	@Override
	public void applyBlockData() {
		this.getBlock().setBlockData(this.data);
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public BlockPriority getPriority() {
		if (this.getBlock().getBlockData() instanceof Attachable) {
			return BlockPriority.ATTACHABLE;
		}
		return BlockPriority.DEFAULT;
	}
}
