package MoseShipsSponge.Algorthum.BlockFinder;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Configs.BlockList;
import MoseShipsSponge.Configs.BlockList.ListType;
import MoseShipsSponge.Utils.LocationUtils;

public class Ships6BlockFinder implements BasicBlockFinder{

	@Override
	public List<Location<World>> getConnectedBlocks(int limit, Location<World> loc) {
		int count = 0;
		Direction[] faces = {Direction.NORTH, Direction.EAST, Direction.DOWN, Direction.SOUTH, Direction.UP, Direction.WEST};
		List<Location<World>> ret = new ArrayList<>();
		List<Location<World>> target = new ArrayList<>();
		List<Location<World>> process = new ArrayList<>();
		process.add(loc);
		while(count != limit){
			if(process.isEmpty()){
				return ret;
			}
			for(int A = 0; A < process.size(); A++){
				Location<World> proc = process.get(A);
				count++;
				for(Direction dir : faces){
					Location<World> block = proc.getBlockRelative(dir);
					if(!LocationUtils.blockWorldContains(ret, block)){
						if(BlockList.BLOCK_LIST.contains(block.getBlock(), ListType.MATERIALS)){
							ret.add(block);
							target.add(block);
						}
					}
				}
			}
			process.clear();
			process.addAll(target);
			target.clear();
		}
		return ret;
	}

	@Override
	public String getName() {
		return "Ships 6";
	}

}
