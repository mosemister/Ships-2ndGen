package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.SpecialSnapshot;

public class SpawnerSnapshot extends BlockSnapshot implements SpecialSnapshot {

	EntityType g_type;
	int g_delay;

	public SpawnerSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof CreatureSpawner) {
			CreatureSpawner spawner = (CreatureSpawner) block.getState();
			g_type = spawner.getSpawnedType();
			g_delay = spawner.getDelay();
		}
	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof CreatureSpawner) {
			CreatureSpawner spawner = (CreatureSpawner) block.getState();
			spawner.setSpawnedType(g_type);
			spawner.setDelay(g_delay);
			spawner.update();
		}
	}

}
