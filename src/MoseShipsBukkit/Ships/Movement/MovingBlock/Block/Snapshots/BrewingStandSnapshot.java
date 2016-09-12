package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class BrewingStandSnapshot extends BlockSnapshot implements SpecialSnapshot {

	Map<Integer, ItemStack> g_inv = new HashMap<Integer, ItemStack>();
	int g_brew_time, g_fuel_level;

	public BrewingStandSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof BrewingStand) {
			BrewingStand stand = (BrewingStand) block.getState();
			BrewerInventory inv = stand.getInventory();
			g_brew_time = stand.getBrewingTime();
			g_fuel_level = stand.getFuelLevel();
			for (int A = 0; A < inv.getSize(); A++) {
				ItemStack item = inv.getItem(A);
				if (item != null) {
					g_inv.put(A, item.clone());
				}
			}
			inv.clear();
		}

	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof BrewingStand) {
			BrewingStand stand = (BrewingStand) block.getState();
			BrewerInventory inv = stand.getInventory();
			for (Entry<Integer, ItemStack> entry : g_inv.entrySet()) {
				inv.setItem(entry.getKey(), entry.getValue());
			}
			stand.setBrewingTime(g_brew_time);
			stand.setFuelLevel(g_fuel_level);
			stand.update();
		}
	}

}
