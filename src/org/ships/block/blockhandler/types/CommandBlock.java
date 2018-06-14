package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class CommandBlock implements BlockHandler<org.bukkit.block.CommandBlock>{

	Block block;
	String command;
	
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
		saveCommand();
		this.block.setType(material);
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}

	@Override
	public void apply() {
		applyCommand();
	}
	
	public void applyCommand() {
		getState().setCommand(this.command);
	}
	
	public void saveCommand() {
		this.command = getState().getCommand();
	}

}
