package MoseShipsBukkit.Utils.Lists;

import java.util.ArrayList;

import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.ShipBlock.BlockState;

public class MovingBlockList extends ArrayList<MovingBlock> {

	private static final long serialVersionUID = 1L;

	public MovingBlockList filterBlocks(BlockState state) {
		MovingBlockList list = new MovingBlockList();
		for (MovingBlock mb : this) {
			if (state.looseMatch(mb.getSnapshot().createState())) {
				list.add(mb);
			}
		}
		return list;
	}

}
