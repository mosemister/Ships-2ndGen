package org.ships.block.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;
import org.ships.configuration.Config;
import org.ships.utils.ListUtil;

public interface ShipsStructure {

	public default Set<BlockHandler<? extends BlockState>> getInbetweenAir(Block block){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		List<BlockHandler<? extends BlockState>> blocks = new ArrayList<>();
		int gap = config.getInt("Structure.StructureLimits.airCheckGap");
		for (int A = 1; A < gap; A++) {
			Block block2 = block.getRelative(0, -A, 0);
			BlockHandler<? extends BlockState> handler = BlockHandler.getBlockHandler(block2);
			if (ListUtil.containsBlockHandler(handler, getAllBlocks())) {
				return new HashSet<>(blocks);
			} else if (block2.getType().equals(Material.AIR)) {
				blocks.add(handler);
			}
		}
		return null;
	}

	public Set<BlockHandler<? extends BlockState>> getAllBlocks();
	
	public default Set<BlockHandler<? extends BlockState>> getAirBlocks(){
		return getAllBlocks().stream().filter(e -> e.getBlock().getType().equals(Material.AIR)).collect(Collectors.toSet());
	}
	
	public default Set<BlockHandler<? extends BlockState>> getBlocks(BlockPriority priority){
		return getAllBlocks().stream().filter(e -> e.getPriority().equals(priority)).collect(Collectors.toSet());
	}

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
