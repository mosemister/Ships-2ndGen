package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.block.BlockState;

import MoseShipsBukkit.ShipBlock.Snapshot.RotatableSnapshot;

public class RedstoneSquareSnapshot extends RedstoneSnapshot implements RotatableSnapshot {

	public RedstoneSquareSnapshot(BlockState state) {
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
				return 1;
			case 3:
				return 2;
			case 4:
				return 3;

			case 5:
				return 8;
			case 6:
				return 5;
			case 7:
				return 6;
			case 8:
				return 7;

			case 9:
				return 12;
			case 10:
				return 9;
			case 11:
				return 10;
			case 12:
				return 11;

			case 13:
				return 16;
			case 14:
				return 13;
			case 15:
				return 14;
			case 16:
				return 15;
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateRight() {
		byte data = getData().getData();
		switch (data) {
			case 1:
				return 2;
			case 2:
				return 3;
			case 3:
				return 4;
			case 4:
				return 1;

			case 5:
				return 6;
			case 6:
				return 7;
			case 7:
				return 8;
			case 8:
				return 6;

			case 9:
				return 10;
			case 10:
				return 11;
			case 11:
				return 12;
			case 12:
				return 9;

			case 13:
				return 14;
			case 14:
				return 15;
			case 15:
				return 16;
			case 16:
				return 13;
		}
		return data;
	}

}
