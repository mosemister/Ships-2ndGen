package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.block.BlockState;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.RotatableSnapshot;

public class LogSnapshot extends BlockSnapshot implements RotatableSnapshot {

	public LogSnapshot(BlockState state) {
		super(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateLeft() {
		byte data = getData().getData();
		switch (data) {
			case 4:
				return 8;
			case 5:
				return 9;

		}
		return 0;
	}

	@Override
	public byte getRotateRight() {
		return getRotateLeft();
	}

}
