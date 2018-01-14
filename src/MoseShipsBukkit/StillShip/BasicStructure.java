package MoseShipsBukkit.StillShip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShipsBukkit.BlockHandler.BlockHandler;

public class BasicStructure implements ShipsStructure {

	List<BlockHandler> handlers = new ArrayList<>();
	
	public BasicStructure(Collection<BlockHandler> collection) {
		handlers.addAll(collection);
	}
	
	@Override
	public Set<BlockHandler> getPriorityBlocks() {
		return null;
	}

	@Override
	public Set<BlockHandler> getStandardBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler> getSpecialBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler> getAirBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler> getInbetweenAir(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler> getAllBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockHandler> getBlocks(Material... materials) {
		// TODO Auto-generated method stub
		return null;
	}

}
