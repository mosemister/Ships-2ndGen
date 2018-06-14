package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.ships.block.blockhandler.BlockPriority;
import org.ships.block.blockhandler.TextHandler;

public class Sign implements TextHandler<org.bukkit.block.Sign> {
	
	protected Block block;
	protected String[] text = new String[0];

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
		saveText();
		block.setType(material);
	}
	
	@Override
	public void apply() {
		applyText();
	}

	@Override
	public void saveText() {
		text = ((org.bukkit.block.Sign)block.getState()).getLines();
	}

	@Override
	public void applyText() {
		for(int A = 0; A < text.length; A++) {
			((org.bukkit.block.Sign)block.getState()).setLine(A, text[A]);
		}
		
	}
	
	@Override
	public BlockPriority getPriority() {
		if(getBlock().getType().equals(Material.WALL_SIGN)) {
			return BlockPriority.ATTACHABLE;
		}
		return BlockPriority.SPECIAL;
	}


}
