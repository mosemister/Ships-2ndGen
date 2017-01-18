package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.block.BlockState;

import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.RotatableSnapshot;

public class StairsSnapshot extends BlockSnapshot implements RotatableSnapshot {

	public StairsSnapshot(BlockState state) {
		super(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateLeft() {
		byte data = getData().getData();
		switch (data) {
			case 0:
				return 3;
			case 1:
				return 2;
			case 2:
				return 0;
			case 3:
				return 1;
			case 4:
				return 7;
			case 5:
				return 6;
			case 6:
				return 4;
			case 7:
				return 5;
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateRight() {
		byte data = getData().getData();
		switch (data) {
			case 0:
				return 2;
			case 1:
				return 3;
			case 2:
				return 1;
			case 3:
				return 0;
			case 4:
				return 6;
			case 5:
				return 7;
			case 6:
				return 5;
			case 7:
				return 4;
		}
		return data;
	}
}
