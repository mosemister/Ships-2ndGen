package MoseShipsSponge.BlockFinder.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.BlockFinder.BasicBlockFinder;
import MoseShipsSponge.Configs.Files.BlockList.ListType;

public class Prototype3 extends BasicBlockFinder{

	int COUNT;
	List<Location<World>> BLOCKS;
	
	@Override
	public List<Location<World>> getConnectedBlocks(int limit, Location<World> loc) {
		BLOCKS = new ArrayList<>();
		getNextBlock(limit, Arrays.asList(Direction.DOWN, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.UP, Direction.WEST), loc);
		return BLOCKS;
	}
	
	void getNextBlock(int limit, List<Direction> direction, Location<World> loc){
		if(COUNT > limit){
			return;
		}
		COUNT++;
		direction.forEach(d ->{
			Location<World> loc2 = loc.getRelative(d);
			if(ShipsMain.getPlugin().getMaterialsList().contains(loc2.getBlock(), ListType.MATERIALS)){
				if (!BLOCKS.contains(loc2)){
					BLOCKS.add(loc2);
					getNextBlock(limit, direction, loc2);
				}
			}
		});
	}

}
