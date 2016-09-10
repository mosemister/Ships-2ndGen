package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.RotatableSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class DispenserSnapshot extends BlockSnapshot implements SpecialSnapshot, RotatableSnapshot {

	Map<Integer, ItemStack> g_inv = new HashMap<Integer, ItemStack>();

	protected DispenserSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof Chest) {
			Chest chest = (Chest) block.getState();
			Inventory inv = chest.getBlockInventory();
			for (int A = 0; A < inv.getSize(); A++) {
				ItemStack item = inv.getItem(A);
				if (item != null) {
					g_inv.put(A, item);
				}
			}
		}
	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof Chest) {
			Chest chest = (Chest) block.getState();
			Inventory inv = chest.getBlockInventory();
			for (Entry<Integer, ItemStack> entry : g_inv.entrySet()) {
				inv.setItem(entry.getKey(), entry.getValue());
			}
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
