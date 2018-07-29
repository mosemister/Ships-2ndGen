package org.ships.block.blockhandler.types;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.block.data.BlockData;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class JukeBox implements BlockHandler<Jukebox> {
	protected Block block;
	protected BlockData data;
	protected Material disk;

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
		return this.getBlock().getState() instanceof Jukebox;
	}

	@Override
	public void save(boolean forRemoval) {
		this.saveBlockData();
		this.saveDisk();
	}

	@Override
	public void apply(Jukebox blockstate) {
		this.applyDisk(blockstate);
	}

	public void applyDisk(Jukebox blockstate) {
		blockstate.setPlaying(this.disk);
	}

	public void saveDisk() {
		this.disk = this.getState().getPlaying();
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
}
