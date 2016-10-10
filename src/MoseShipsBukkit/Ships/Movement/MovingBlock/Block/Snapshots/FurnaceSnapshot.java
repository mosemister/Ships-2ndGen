package MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.RotatableSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.SpecialSnapshot;

public class FurnaceSnapshot extends BlockSnapshot implements RotatableSnapshot, SpecialSnapshot {

	ItemStack g_fuel, g_burn, g_result;

	public FurnaceSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof Furnace) {
			Furnace furnace = (Furnace) block.getState();
			FurnaceInventory inv = furnace.getInventory();
			ItemStack fuel = inv.getFuel();
			if (fuel != null) {
				g_fuel = fuel.clone();
			}
			ItemStack burn = inv.getSmelting();
			if (burn != null) {
				g_burn = burn.clone();
			}
			ItemStack result = inv.getResult();
			if (result != null) {
				g_result = result.clone();
			}
			inv.clear();
		}
	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof Furnace) {
			Furnace furnace = (Furnace) block.getState();
			FurnaceInventory inv = furnace.getInventory();
			inv.setFuel(g_fuel);
			inv.setResult(g_result);
			inv.setSmelting(g_burn);
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
