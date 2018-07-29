package org.ships.block.blockhandler.types;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class CommandBlock implements BlockHandler<org.bukkit.block.CommandBlock> {
	Block block;
	String command;
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
		return this.getBlock().getState() instanceof org.bukkit.block.CommandBlock;
	}

	@Override
	public void save(boolean forRemoval) {
		this.saveBlockData();
		this.saveCommand();
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}

	@Override
	public void apply(org.bukkit.block.CommandBlock blockstate) {
		this.applyCommand(blockstate);
	}

	public void applyCommand(org.bukkit.block.CommandBlock blockstate) {
		blockstate.setCommand(this.command);
	}

	public void saveCommand() {
		this.command = this.getState().getCommand();
	}
}
