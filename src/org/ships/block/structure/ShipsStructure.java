package org.ships.block.structure;

import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.ships.block.blockhandler.BlockHandler;

public interface ShipsStructure {

	public Set<BlockHandler<? extends BlockState>> getPriorityBlocks();

	public Set<BlockHandler<? extends BlockState>> getStandardBlocks();

	public Set<BlockHandler<? extends BlockState>> getSpecialBlocks();

	public Set<BlockHandler<? extends BlockState>> getAirBlocks();

	public Set<BlockHandler<? extends BlockState>> getInbetweenAir(Block block);

	public Set<BlockHandler<? extends BlockState>> getAllBlocks();

	public default Set<BlockHandler<? extends BlockState>> getBlocks(Material... materials) {
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
