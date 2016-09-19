package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.FlowerPot;
import org.bukkit.material.MaterialData;

import MoseShipsBukkit.VersionChecking;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.AttachableSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class PotSnapshot extends BlockSnapshot implements SpecialSnapshot, AttachableSnapshot {

	MaterialData g_data;

	public PotSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		VersionChecking.VersionOutcome outcome = VersionChecking.isGreater(VersionChecking.MINECRAFT_VERSION, 1, 10, 0);
		if (outcome != VersionChecking.VersionOutcome.LOWER) {
			if (block.getState() instanceof FlowerPot) {
				FlowerPot pot = (FlowerPot) block.getState();
				g_data = pot.getContents();
				pot.setContents(null);
				pot.update();
			}
		}
	}

	@Override
	public void onPlace(Block block) {
		if (!VersionChecking.isGreater(VersionChecking.MINECRAFT_VERSION, 1, 10, 0)
				.equals(VersionChecking.VersionOutcome.LOWER)) {
			if (block.getState() instanceof FlowerPot) {
				FlowerPot pot = (FlowerPot) block.getState();
				pot.setContents(g_data);
				pot.update();
			}
		}
	}

}
