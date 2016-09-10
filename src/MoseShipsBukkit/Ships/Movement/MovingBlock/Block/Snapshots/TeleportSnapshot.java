package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.EndGateway;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.RotatableSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class TeleportSnapshot extends BlockSnapshot implements SpecialSnapshot, RotatableSnapshot {

	boolean g_exact;
	Location g_exit;

	protected TeleportSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof EndGateway) {
			EndGateway gateway = (EndGateway) block.getState();
			g_exact = gateway.isExactTeleport();
			g_exit = gateway.getExitLocation();
		}
	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof EndGateway) {
			EndGateway gateway = (EndGateway) block.getState();
			gateway.setExactTeleport(g_exact);
			gateway.setExitLocation(g_exit);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte getRotateLeft() {
		byte data = this.getData().getData();
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
		byte data = this.getData().getData();
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
