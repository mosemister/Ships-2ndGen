package org.ships.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.ships.block.blockhandler.BlockHandler;

public class ListUtil {
	public static boolean containsBlock(Block block, Block... blocks) {
		return ListUtil.containsBlock(block, Arrays.asList(blocks));
	}

	public static boolean containsBlock(Block block, Collection<Block> blocks) {
		return blocks.stream().anyMatch(b -> b.getX() == block.getX() && b.getY() == block.getY() && b.getZ() == block.getZ() && b.getWorld().equals(block.getWorld()));
	}

	public static boolean containsBlockHandler(BlockHandler<? extends BlockState> handler, Collection<BlockHandler<? extends BlockState>> collection) {
		return collection.stream().anyMatch(b -> ListUtil.containsBlock(handler.getBlock(), b.getBlock()));
	}

	public static <T> String asStringList(Collection<T> collection, String splitter, Function<? super T, ? extends String> toString) {
		String ret = null;
		for (T type : collection) {
			if (ret == null) {
				ret = toString.apply(type);
				continue;
			}
			ret = ret + splitter + toString.apply(type);
		}
		return ret;
	}
}
