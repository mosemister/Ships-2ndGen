package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import MoseShipsBukkit.ShipBlock.Snapshot.AttachableSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.SpecialSnapshot;

public class SkullSnapshot extends BlockSnapshot implements SpecialSnapshot, AttachableSnapshot {

	String g_owner;
	BlockFace g_rotation;
	SkullType g_type;

	public SkullSnapshot(BlockState state) {
		super(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof Skull) {
			Skull skull = (Skull) block.getState();
			g_owner = skull.getOwner();
			g_rotation = skull.getRotation();
			g_type = skull.getSkullType();
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof Skull) {
			Skull skull = (Skull) block.getState();
			skull.setOwner(g_owner);
			skull.setRotation(g_rotation);
			skull.setSkullType(g_type);
			skull.update();
		}
	}

}
