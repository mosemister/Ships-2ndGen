package org.ships.block.blockhandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Rotatable;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;

public interface BlockHandler {
	
	public static final Map<Material, Class<BlockHandler>> HANDLER = new HashMap<>(); 
	
	public Block getBlock();
	public void setBlock(Block block);
	public void remove(Material material);
	public void apply();
	public default void rotate(MovementMethod method) {
		if(!(getBlock().getBlockData() instanceof Rotatable)) {
			return;
		}
		Rotatable rotate = (Rotatable)getBlock().getBlockData();
		switch(method) {
		case ROTATE_RIGHT:
			rotate.setRotation(Ships.getSideFace(rotate.getRotation(), false));
			break;
		case ROTATE_LEFT:
			rotate.setRotation(Ships.getSideFace(rotate.getRotation(), true));
			break;
		default: break;
		}
	}
	public default BlockPriority getPriority() {
		if(getBlock().getBlockData() instanceof Attachable) {
			return BlockPriority.ATTACHABLE;
		}
		return BlockPriority.DEFAULT;
	}
	
	public static BlockHandler getBlockHandler(Block block) {
		//TODO
		return null;
	}
	
	public static Set<BlockHandler> convert(Collection<Block> blocks){
		List<BlockHandler> list = new ArrayList<>();
		blocks.stream().forEach(b -> list.add(getBlockHandler(b)));
		return new HashSet<>(list);
	}

}
