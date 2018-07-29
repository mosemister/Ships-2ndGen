package org.ships.block.blockhandler;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ships.plugin.BlockHandlerNotReady;

public abstract class Container<T extends org.bukkit.block.Container> implements InventoryHandler<T> {
	protected Block block;
	protected BlockData data;
	protected Inventory inventory;
	protected String lock;

	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void applyBlockData() {
		this.getBlock().setBlockData(this.data);
	}

	@Override
	public void saveBlockData() {
		this.data = this.getBlock().getBlockData();
	}

	@Override
	public void save(boolean forRemoval) throws BlockHandlerNotReady {
		if (!this.isReady()) {
			throw new BlockHandlerNotReady(this, "the target block is not the correct material");
		}
		this.saveBlockData();
		this.saveInventory();
		this.saveLock();
	}

	@Override
	public void apply(T blockstate) {
		this.applyLock(blockstate);
		this.applyInventory(blockstate);
	}

	@Override
	public void saveInventory() {
		this.inventory = this.getState().getSnapshotInventory();
	}

	@Override
	public void applyInventory(T container) {
		int items = 0;
		for (ItemStack item : this.inventory) {
			if (item == null || item.getType().equals(Material.AIR))
				continue;
			++items;
		}
		System.out.println("Container: Inventory Size: " + items + ": Is placed: " + container.isPlaced());
		Inventory inv = container.getInventory();
		for (int A = 0; A < this.inventory.getSize(); ++A) {
			ItemStack item = this.inventory.getItem(A);
			inv.setItem(A, item);
			if (item == null || item.getType().equals(Material.AIR))
				continue;
			System.out.println("\tAdded item: " + item.getType().name() + ": Size: " + item.getAmount() + ": Pos:" + A);
		}
	}

	public void saveLock() {
		this.lock = getState().getLock();
	}

	public void applyLock(T container) {
		container.setLock(this.lock);
	}
}
