package MoseShipsBukkit.Algorthum.BlockFinder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

public interface BasicBlockFinder {

	static final List<BasicBlockFinder> LIST = new ArrayList<BasicBlockFinder>();

	public List<Block> getConnectedBlocks(int limit, Block loc);

	public String getName();

}
