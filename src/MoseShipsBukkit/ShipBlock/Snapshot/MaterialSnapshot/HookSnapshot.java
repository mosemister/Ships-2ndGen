package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.block.BlockState;

import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.RotatableSnapshot;

public class HookSnapshot extends BlockSnapshot implements RotatableSnapshot {

	public HookSnapshot(BlockState state) {
		super(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateLeft() {
		byte data = getData().getData();
		switch (data) {
			case 2:
				return 4;
			case 3:
				return 5;
			case 4:
				return 3;
			case 5:
				return 2;
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateRight() {
		byte data = getData().getData();
		switch (data) {
			case 2:
				return 5;
			case 3:
				return 4;
			case 4:
				return 2;
			case 5:
				return 3;
		}
		return data;
	}

}
