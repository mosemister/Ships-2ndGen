package MoseShipsBukkit.Utils.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.ShipBlock.BlockState;

public class MovingBlockList extends ArrayList<MovingBlock> {

	private static final long serialVersionUID = 1L;

	public MovingBlockList filterBlocks(BlockState... states){
		return filterBlocks(Arrays.asList(states));
	}
	
	public MovingBlockList filterBlocks(List<BlockState> states) {
		MovingBlockList list = new MovingBlockList();
		for (MovingBlock mb : this) {
			for(BlockState state : states){
				if (state.looseMatch(mb.getSnapshot().createState())) {
					list.add(mb);
					break;
				}
			}
		}
		return list;
	}

}
