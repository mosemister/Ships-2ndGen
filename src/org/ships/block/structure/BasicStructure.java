package org.ships.block.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.ships.block.blockhandler.BlockHandler;

public class BasicStructure implements ShipsStructure {

	List<BlockHandler<? extends BlockState>> handlers = new ArrayList<>();
		
	public BasicStructure() {
		
	}
	
	public BasicStructure(Collection<BlockHandler<? extends BlockState>> collection) {
		handlers.addAll(collection);
	}
	
	@Override
	public Set<BlockHandler<? extends BlockState>> getPriorityBlocks() {
		return null;
	}

	@Override
	public Set<BlockHandler<? extends BlockState>> getStandardBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler<? extends BlockState>> getSpecialBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler<? extends BlockState>> getAirBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler<? extends BlockState>> getInbetweenAir(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler<? extends BlockState>> getAllBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler<? extends BlockState>> getBlocks(Material... materials) {
		// TODO Auto-generated method stub
		return null;
	}

}
