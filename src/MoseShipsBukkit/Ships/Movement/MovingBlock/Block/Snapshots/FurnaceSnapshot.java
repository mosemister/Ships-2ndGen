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

	ItemStack fuel, burn, result;

	protected FurnaceSnapshot(BlockState state) {
		super(state);
	}

	@Override
	public void onRemove(Block block) {
		if (block.getState() instanceof Furnace) {
			Furnace furnace = (Furnace) block.getState();
			FurnaceInventory inv = furnace.getInventory();
			fuel = inv.getFuel();
			burn = inv.getSmelting();
			result = inv.getResult();
		}
	}

	@Override
	public void onPlace(Block block) {
		if (block.getState() instanceof Furnace) {
			Furnace furnace = (Furnace) block.getState();
			FurnaceInventory inv = furnace.getInventory();
			inv.setFuel(fuel);
			inv.setResult(result);
			inv.setSmelting(burn);
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
