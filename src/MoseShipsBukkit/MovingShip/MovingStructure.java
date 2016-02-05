package MoseShipsBukkit.MovingShip;

import java.util.ArrayList;
import java.util.List;

import MoseShipsBukkit.StillShip.ShipsStructure;

public class MovingStructure extends ShipsStructure {

	List<MovingBlock> PRI_BLOCKS = new ArrayList<MovingBlock>();
	List<MovingBlock> SPECIAL_BLOCKS = new ArrayList<MovingBlock>();
	List<MovingBlock> STANDARD_BLOCKS = new ArrayList<MovingBlock>();

	public MovingStructure(List<MovingBlock> blocks) {
		super(MovingBlock.convertToBlockArray(blocks));
		for (MovingBlock block : blocks) {
			if (this.getPriorityBlocks().contains(block.getBlock())) {
				PRI_BLOCKS.add(block);
			} else if (this.getStandardBlocks().contains(block.getBlock())) {
				STANDARD_BLOCKS.add(block);
			} else if (block.getSpecialBlock() != null) {
				SPECIAL_BLOCKS.add(block);
			}
		}
	}

	public List<MovingBlock> getPriorityMovingBlocks() {
		return PRI_BLOCKS;
	}

	public List<MovingBlock> getSpecialMovingBlocks() {
		return SPECIAL_BLOCKS;
	}

	public List<MovingBlock> getStandardMovingBlocks() {
		return STANDARD_BLOCKS;
	}

	public List<MovingBlock> getAllMovingBlocks() {
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		blocks.addAll(getPriorityMovingBlocks());
		blocks.addAll(getSpecialMovingBlocks());
		blocks.addAll(getStandardMovingBlocks());
		return blocks;
	}
}
