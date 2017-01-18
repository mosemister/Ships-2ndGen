package MoseShipsBukkit.ShipBlock.Snapshot;

import org.bukkit.block.Block;

public interface SpecialSnapshot {

	public void onRemove(Block block);

	public void onPlace(Block block);

}
