package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class Head implements BlockHandler<Skull>{
	
	protected Block block;
	protected OfflinePlayer owner;

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void remove(Material material) {
		saveOwner();
		block.setType(material);
	}
	
	@Override
	public void apply() {
		applyOwner();
	}

	public void saveOwner() {
		this.owner = getState().getOwningPlayer();
	}

	public void applyOwner() {
		getState().setOwningPlayer(this.owner);		
	}
	
	@Override
	public BlockPriority getPriority() {
		return BlockPriority.ATTACHABLE;
	}
}