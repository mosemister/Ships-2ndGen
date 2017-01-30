package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.FlowerPot;
import org.bukkit.material.MaterialData;

import MoseShipsBukkit.ShipBlock.Snapshot.AttachableSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.SpecialSnapshot;
import MoseShipsBukkit.Utils.VersionCheckingUtil;

public class PotSnapshot extends BlockSnapshot implements SpecialSnapshot, AttachableSnapshot {

	MaterialData g_data;

	public PotSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		VersionCheckingUtil.VersionOutcome outcome = VersionCheckingUtil
				.isGreater(VersionCheckingUtil.MINECRAFT_VERSION, 1, 10, 0);
		if (outcome != VersionCheckingUtil.VersionOutcome.LOWER) {
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
		if (!VersionCheckingUtil.isGreater(VersionCheckingUtil.MINECRAFT_VERSION, 1, 10, 0)
				.equals(VersionCheckingUtil.VersionOutcome.LOWER)) {
			if (block.getState() instanceof FlowerPot) {
				FlowerPot pot = (FlowerPot) block.getState();
				pot.setContents(g_data);
				pot.update();
			}
		}
	}

}
