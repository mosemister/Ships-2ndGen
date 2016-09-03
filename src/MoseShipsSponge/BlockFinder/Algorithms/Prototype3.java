package MoseShipsSponge.BlockFinder.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.BlockFinder.BasicBlockFinder;
import MoseShipsSponge.Configs.Files.BlockList;
import MoseShipsSponge.Configs.Files.BlockList.ListType;

public class Prototype3 implements BasicBlockFinder {

	int COUNT;
	List<Block> BLOCKS;

	@Override
	public List<Block> getConnectedBlocks(int limit, Block loc) {
		BLOCKS = new ArrayList<Block>();
		BlockFace[] faces = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST};
		getNextBlock(limit, loc, faces);
		return BLOCKS;
	}

	void getNextBlock(int limit, Block loc, BlockFace... directions) {
		if (COUNT > limit) {
			return;
		}
		COUNT++;
		for(BlockFace face : directions){
			Block block = loc.getRelative(face);
			if(BlockList.BLOCK_LIST.contains(block, ListType.MATERIALS)){
				if(!BLOCKS.contains(block)){
					BLOCKS.add(block);
					getNextBlock(limit, block, directions);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Ships 5";
	}

}
