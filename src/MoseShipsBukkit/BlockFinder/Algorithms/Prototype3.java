package MoseShipsBukkit.BlockFinder.Algorithms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.BlockFinder.BasicBlockFinder;
import MoseShipsBukkit.Configs.Files.BlockList;
import MoseShipsBukkit.Configs.Files.BlockList.ListType;

public class Prototype3 implements BasicBlockFinder {

	int COUNT;
	List<Block> BLOCKS;

	@Override
	public List<Block> getConnectedBlocks(int limit, Block loc) {
		if (loc == null) {
			throw new NullPointerException();
		}
		BLOCKS = new ArrayList<Block>();
		COUNT = 0;
		BlockFace[] faces = {
			BlockFace.DOWN,
			BlockFace.EAST,
			BlockFace.NORTH,
			BlockFace.SOUTH,
			BlockFace.UP,
			BlockFace.WEST
		};
		getNextBlock(limit, loc, faces);
		return BLOCKS;
	}

	@SuppressWarnings("deprecation")
	void getNextBlock(int limit, Block loc, BlockFace... directions) {
		if (COUNT == limit) {
			return;
		}
		COUNT++;
		for (BlockFace face : directions) {
			Block block = loc.getRelative(face);
			if (BlockList.BLOCK_LIST.contains(block.getType(), block.getData(), ListType.MATERIALS)) {
				if (!BLOCKS.contains(block)) {
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
