package org.ships.block.blockhandler.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

public class Banner implements BlockHandler<org.bukkit.block.Banner>{

	Block block;
	DyeColor baseColor;
	List<Pattern> patterns = new ArrayList<>();
	
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
		savePatterns();
		applyBaseColor();
		this.block.setType(material);
	}

	@Override
	public BlockPriority getPriority() {
		return BlockPriority.ATTACHABLE;
	}

	@Override
	public void apply() {
		applyPatterns();
		applyBaseColor();
	}
	
	public void applyPatterns() {
		getState().setPatterns(this.patterns);
	}
	
	public void savePatterns() {
		this.patterns = getState().getPatterns();
	}
	
	public void applyBaseColor() {
		getState().setBaseColor(this.baseColor);
	}
	
	public void saveBaseColor() {
		this.baseColor = getState().getBaseColor();
	}

}
