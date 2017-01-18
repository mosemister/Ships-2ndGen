package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jukebox;

import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.SpecialSnapshot;

public class JukeBoxSnapshot extends BlockSnapshot implements SpecialSnapshot {

	Material g_material;

	public JukeBoxSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof Jukebox) {
			Jukebox box = (Jukebox) block.getState();
			g_material = box.getPlaying();
			box.setPlaying(null);
			box.update();
		}
	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof Jukebox) {
			Jukebox box = (Jukebox) block.getState();
			box.setPlaying(g_material);
			box.update();
		}
	}

}
