package MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.RotatableSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.SpecialSnapshot;

public class DropperSnapshot extends BlockSnapshot implements SpecialSnapshot, RotatableSnapshot {

	Map<Integer, ItemStack> g_inv = new HashMap<Integer, ItemStack>();

	public DropperSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof Dropper) {
			Dropper dropper = (Dropper) block.getState();
			Inventory inv = dropper.getInventory();
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
		if (block.getState() instanceof Dropper) {
			Dropper drop = (Dropper) block.getState();
			Inventory inv = drop.getInventory();
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
