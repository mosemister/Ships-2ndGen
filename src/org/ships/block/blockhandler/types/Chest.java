package org.ships.block.blockhandler.types;

import org.ships.block.blockhandler.Container;

public class Chest extends Container<org.bukkit.block.Chest> {

	@Override
	public boolean isReady() {
		return this.getBlock().getState() instanceof org.bukkit.block.Chest;
	}

	@Override
	public void saveInventory() {
		this.inventory = this.getState().getBlockInventory();
	}

	@Override
	public void applyInventory(org.bukkit.block.Chest chest) {
		for (int A = 0; A < this.inventory.getSize(); ++A) {
			chest.getInventory().setItem(A, this.inventory.getItem(A));
		}
	}
}
