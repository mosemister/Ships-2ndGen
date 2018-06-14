package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class JukeBox implements BlockHandler<Jukebox>{
	
	protected Block block;
	protected Material disk;

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
		saveDisk();
		block.setType(material);
	}
	
	@Override
	public void apply() {
		applyDisk();
	}

	public void applyDisk() {
		getState().setPlaying(disk);
	}
	
	public void saveDisk() {
		this.disk = getState().getPlaying();
	}
	
	@Override
	public BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}

}
