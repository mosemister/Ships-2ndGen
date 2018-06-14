package org.ships.block.blockhandler;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

public class Container<T extends org.bukkit.block.Container> implements InventoryHandler<T> {

	protected Block block;
	protected Inventory inventory;
	protected String lock;
	
	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void remove(Material material) {
		saveInventory();
		saveLock();
		this.block.setType(material);
	}

	@Override
	public void apply() {
		applyInventory();
		applyLock();
	}

	@Override
	public void saveInventory() {
		this.inventory = ((org.bukkit.block.Container)block.getState()).getSnapshotInventory();
	}

	@Override
	public void applyInventory() {
		org.bukkit.block.Container container = (org.bukkit.block.Container)block.getState();
		for(int A = 0; A < this.inventory.getSize(); A++) {
			container.getInventory().setItem(A, this.inventory.getItem(A));
		}
	}
	
	public void saveLock() {
		org.bukkit.block.Container container = (org.bukkit.block.Container)block.getState();
		this.lock = container.getLock();
	}
	
	public void applyLock() {
		org.bukkit.block.Container container = (org.bukkit.block.Container)block.getState();
		container.setLock(this.lock);
	}
}
