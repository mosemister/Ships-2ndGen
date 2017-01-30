package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.block.BlockState;

import MoseShipsBukkit.ShipBlock.Snapshot.AttachableSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.RotatableSnapshot;

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
