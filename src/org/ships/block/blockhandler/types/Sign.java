package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.ships.block.blockhandler.BlockPriority;
import org.ships.block.blockhandler.TextHandler;

public class Sign implements TextHandler<org.bukkit.block.Sign> {
	protected Block block;
	protected BlockData data;
	protected String[] text = new String[0];

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
		return this.getBlock().getState() instanceof org.bukkit.block.Sign;
	}

	@Override
	public void saveText() {
		this.text = this.getState().getLines();
	}

	@Override
	public void applyText(org.bukkit.block.Sign state) {
		for (int A = 0; A < this.text.length; ++A) {
			state.setLine(A, this.text[A]);
		}
	}

	@Override
	public BlockPriority getPriority() {
		if (this.getBlock().getType().equals(Material.WALL_SIGN)) {
			return BlockPriority.ATTACHABLE;
		}
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
}
