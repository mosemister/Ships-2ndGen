package org.ships.block.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;

import MoseShipsBukkit.MovingShip.MovingBlock;

public class MovingStructure implements ShipsStructure {

	List<MovingBlock> blocks = new ArrayList<>();
	
	public MovingStructure(Collection<MovingBlock> blocks) {
		this.blocks.addAll(blocks);
	}

	@Override
	public Set<BlockHandler> getPriorityBlocks() {
		List<BlockHandler> list = new ArrayList<>();
		blocks.stream().filter(h -> h.getHandle().getPriority().equals(BlockPriority.ATTACHABLE)).forEach(b -> list.add(b.getHandle()));
		return new HashSet<>(list);
	}

	@Override
	public Set<BlockHandler> getStandardBlocks() {
		List<BlockHandler> list = new ArrayList<>();
		blocks.stream().filter(h -> h.getHandle().getPriority().equals(BlockPriority.DEFAULT)).filter(e -> (!e.getBlock().getType().equals(Material.AIR))).forEach(b -> list.add(b.getHandle()));
		return new HashSet<>(list);
	}

	@Override
	public Set<BlockHandler> getSpecialBlocks() {
		List<BlockHandler> list = new ArrayList<>();
		blocks.stream().filter(h -> h.getHandle().getPriority().equals(BlockPriority.SPECIAL)).forEach(b -> list.add(b.getHandle()));
		return new HashSet<>(list);
	}

	@Override
	public Set<BlockHandler> getAirBlocks() {
		List<BlockHandler> list = new ArrayList<>();
		blocks.stream().filter(e -> (!e.getBlock().getType().equals(Material.AIR))).forEach(b -> list.add(b.getHandle()));
		return new HashSet<>(list);
	}

	@Override
	public Set<BlockHandler> getInbetweenAir(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler> getAllBlocks() {
		List<BlockHandler> list = new ArrayList<>();
		blocks.stream().forEach(b -> list.add(b.getHandle()));
		return new HashSet<>(list);
	}
}
