package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.DyeColor;
import org.bukkit.block.Bed;

import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.SpecialSnapshot;
import MoseShipsBukkit.Utils.VersionCheckingUtil;

public class BedSnapshot extends BlockSnapshot implements SpecialSnapshot{

	DyeColor g_color;
	
	protected BedSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		VersionCheckingUtil.VersionOutcome outcome = VersionCheckingUtil
				.isGreater(VersionCheckingUtil.MINECRAFT_VERSION, 1, 12, 0);
		if (outcome == VersionCheckingUtil.VersionOutcome.LOWER) {
			return;
		}
		if(!(block.getState() instanceof Bed)) {
			return;
		}
		Bed bed = (Bed)block.getState();
		g_color = bed.getColor();
	}

	@Override
	public void onPlace(Block block) {
		VersionCheckingUtil.VersionOutcome outcome = VersionCheckingUtil
				.isGreater(VersionCheckingUtil.MINECRAFT_VERSION, 1, 12, 0);
		if (outcome == VersionCheckingUtil.VersionOutcome.LOWER) {
			return;
		}
		if(!(block.getState() instanceof Bed)) {
			return;
		}
		Bed bed = (Bed)block.getState();
		bed.setColor(g_color);
	}

}
