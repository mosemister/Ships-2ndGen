package MoseShipsBukkit.Algorthum.BlockFinder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

/**
 * This interface is the interface used to create your own version of a block
 * finder. If you want to create your own algorithm for detecting structures,
 * you will interface with this interface.
 */

public interface BasicBlockFinder {

	/**
	 * This is all the block finders that Ships knows about, this means you can
	 * get your own block finder even if the server owner does not have your
	 * block finder as the default one.
	 */
	static final List<BasicBlockFinder> LIST = new ArrayList<BasicBlockFinder>();

	/**
	 * this is called to find all blocks that are connected to the original
	 * block. please note that your algorithm must take the block material list
	 * into consideration
	 * 
	 * @param limit
	 *            (the max amount of attempts before your algorithm gives up -
	 *            if possible)
	 * @param loc
	 *            (the original block, this will never be null)
	 * @return (all blocks connected - does not care about the order)
	 */
	public List<Block> getConnectedBlocks(int limit, Block loc);

	/**
	 * the name of the algorithm for if/when the server owner changes the
	 * default algorithm to yours
	 * 
	 * @return
	 */
	public String getName();

}
