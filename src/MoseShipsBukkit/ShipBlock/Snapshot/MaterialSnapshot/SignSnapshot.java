package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import MoseShipsBukkit.ShipBlock.Snapshot.AttachableSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.RotatableSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.SpecialSnapshot;

public class SignSnapshot extends BlockSnapshot implements RotatableSnapshot, SpecialSnapshot, AttachableSnapshot {

	String line1, line2, line3, line4;

	public SignSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof Sign) {
			Sign sign = (Sign) block.getState();
			line1 = sign.getLine(0);
			line2 = sign.getLine(1);
			line3 = sign.getLine(2);
			line4 = sign.getLine(3);
		}
	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof Sign) {
			Sign sign = (Sign) block.getState();
			sign.setLine(0, line1);
			sign.setLine(1, line2);
			sign.setLine(2, line3);
			sign.setLine(3, line4);
			sign.update(true);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateLeft() {
		byte data = this.getData().getData();
		if (getMaterial().equals(Material.WALL_SIGN)) {
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
		} else if (getMaterial().equals(Material.SIGN_POST)) {
			switch (data) {
				case 0:
					return 4;
				case 4:
					return 8;
				case 8:
					return 12;
				case 12:
					return 0;
				case 14:
					return 2;
				case 10:
					return 14;
				case 6:
					return 10;
				case 2:
					return 6;
			}
			return data;
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateRight() {
		byte data = this.getData().getData();
		if (getMaterial().equals(Material.WALL_SIGN)) {
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
		} else if (getMaterial().equals(Material.SIGN_POST)) {
			switch (data) {
				case 4:
					return 0;
				case 8:
					return 4;
				case 12:
					return 8;
				case 0:
					return 12;
				case 2:
					return 14;
				case 14:
					return 10;
				case 10:
					return 6;
				case 6:
					return 2;
			}
		}
		return data;
	}

}
