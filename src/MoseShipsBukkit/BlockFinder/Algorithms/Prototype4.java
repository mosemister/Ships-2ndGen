package MoseShipsBukkit.BlockFinder.Algorithms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.BlockFinder.BasicBlockFinder;
import MoseShipsBukkit.Configs.Files.BlockList;
import MoseShipsBukkit.Configs.Files.BlockList.ListType;
import MoseShipsBukkit.Utils.LocationUtils;

public class Prototype4 implements BasicBlockFinder {
	
	@SuppressWarnings("deprecation")
	@Override
	public List<Block> getConnectedBlocks(int limit, Block loc) {
		new IOException("Checking").printStackTrace();
		int count = 0;
		BlockFace[] faces = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST};
		List<Block> ret = new ArrayList<Block>();
		List<Block> target = new ArrayList<Block>();
		List<Block> process = new ArrayList<Block>();
		process.add(loc);
		while(count != limit){
			if(process.isEmpty()){
				return ret;
			}
			for(int A = 0; A < process.size(); A++){
				Block proc = process.get(A);
				count++;
				for(BlockFace face : faces){
					Block block = proc.getRelative(face);
					if(!LocationUtils.blockContains(ret, block)){
						if(BlockList.BLOCK_LIST.contains(block.getType(), block.getData(), ListType.MATERIALS)){
							ret.add(block);
							target.add(block);
						}
					}
				}
			}
			System.out.println("Before: Process: " + process.size() + " Target: " + target.size() + " Return: " + ret.size());
			process.clear();
			process.addAll(target);
			target.clear();
			System.out.println("After: Process: " + process.size() + " Target: " + target.size() + " Return: " + ret.size());
		}
		return ret;
	}

	@Override
	public String getName() {
		return "Ships 6";
	}

}
