package org.ships.utils;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.ships.block.blockhandler.BlockHandler;

public class ListUtil {
	
	public static boolean containsBlock(Block block, Block... blocks) {
		return containsBlock(block, Arrays.asList(blocks));
	}
	
	public static boolean containsBlock(Block block, Collection<Block> blocks) {
		return blocks.stream().anyMatch(b -> (b.getX() == block.getX()) && (b.getY() == block.getY()) && (b.getZ() == block.getZ()) && (b.getWorld().equals(block.getWorld())));
	}
	
	public static boolean containsBlockHandler(BlockHandler<? extends BlockState> handler, Collection<BlockHandler<? extends BlockState>> collection) {
		return collection.stream().anyMatch(b -> containsBlock(handler.getBlock(), b.getBlock()));
	}

}
