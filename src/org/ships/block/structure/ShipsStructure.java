package org.ships.block.structure;

import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.ships.block.blockhandler.BlockHandler;

public interface ShipsStructure {

	public Set<BlockHandler> getPriorityBlocks();

	public Set<BlockHandler> getStandardBlocks();

	public Set<BlockHandler> getSpecialBlocks();

	public Set<BlockHandler> getAirBlocks();

	public Set<BlockHandler> getInbetweenAir(Block block);

	public Set<BlockHandler> getAllBlocks();

	public default Set<BlockHandler> getBlocks(Material... materials) {
		return getAllBlocks().stream().filter(e -> {
			for (Material material : materials) {
				if (material.equals(e.getBlock().getType())) {
					return true;
				}
			}
			return false;
		}).collect(Collectors.toSet());
	}
}
