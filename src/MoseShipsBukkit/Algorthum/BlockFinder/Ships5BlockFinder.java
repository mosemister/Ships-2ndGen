package MoseShipsBukkit.Algorthum.BlockFinder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Configs.BlockList;
import MoseShipsBukkit.Configs.BlockList.ListType;

/**
 * This is the algorthum for block finding that was used within 
 * the Ships 5. This was reconstructed from the original Ships but
 * then modified to suit the calls of Ships 5 and then again Ships 6
 *
 */
public class Ships5BlockFinder implements BasicBlockFinder {

	
	int COUNT;
	List<Block> BLOCKS;

	//this sets up the algorithm before it starts
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
				BlockFace.WEST };
		getNextBlock(limit, loc, faces);
		return BLOCKS;
	}

	/* 
	 * this is the actual algorithm, this calls itself from inside, that
	 * was the original problem on why large ships did not work in the
	 * original versions of Ships.
	 */
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
