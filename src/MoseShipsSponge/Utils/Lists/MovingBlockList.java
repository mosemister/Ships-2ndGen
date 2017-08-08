package MoseShipsSponge.Utils.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.spongepowered.api.block.BlockState;

import MoseShipsSponge.Movement.MovingBlock;

public class MovingBlockList extends ArrayList<MovingBlock> {

	private static final long serialVersionUID = 1L;

	public MovingBlockList filterBlocks(BlockState... states) {
		return filterBlocks(Arrays.asList(states));
	}

	public MovingBlockList filterBlocks(Collection<BlockState> states) {
		MovingBlockList list = new MovingBlockList();
		stream().filter(mb -> states.stream().anyMatch(b -> b.equals(mb.getState()))).forEach(mb -> list.add(mb));
		return list;
	}

}
