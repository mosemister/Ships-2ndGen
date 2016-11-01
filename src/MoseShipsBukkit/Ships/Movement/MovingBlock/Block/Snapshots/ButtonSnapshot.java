package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.block.BlockState;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.AttachableSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.RotatableSnapshot;

public class ButtonSnapshot extends BlockSnapshot implements AttachableSnapshot, RotatableSnapshot {

	public ButtonSnapshot(BlockState state) {
		super(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateLeft() {
		byte data = getData().getData();
		switch (data) {
			case 1:
				return 4;
			case 2:
				return 3;
			case 3:
				return 1;
			case 4:
				return 2;
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateRight() {
		byte data = getData().getData();
		switch (data) {
			case 1:
				return 3;
			case 2:
				return 4;
			case 3:
				return 2;
			case 4:
				return 1;
		}
		return data;
	}

}
