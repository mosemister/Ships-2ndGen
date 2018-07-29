package org.ships.block.blockhandler.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.data.BlockData;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class Banner implements BlockHandler<org.bukkit.block.Banner> {

	Block block;
	BlockData data;
	DyeColor baseColor;
	List<Pattern> patterns = new ArrayList<Pattern>();

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
		return this.getBlock().getState() instanceof org.bukkit.block.Banner;
	}

	@Override
	public void save(boolean forRemoval) {
		this.saveBlockData();
		this.savePatterns();
		this.saveBaseColor();
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.ATTACHABLE;
	}

	@Override
	public void apply(org.bukkit.block.Banner banner) {
		this.applyPatterns(banner);
		this.applyBaseColor(banner);
	}

	public void applyPatterns(org.bukkit.block.Banner banner) {
		banner.setPatterns(this.patterns);
	}

	public void savePatterns() {
		this.patterns = this.getState().getPatterns();
	}

	public void applyBaseColor(org.bukkit.block.Banner banner) {
		banner.setBaseColor(this.baseColor);
	}

	public void saveBaseColor() {
		this.baseColor = this.getState().getBaseColor();
	}
}
