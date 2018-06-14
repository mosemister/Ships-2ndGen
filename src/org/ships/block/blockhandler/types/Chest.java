package org.ships.block.blockhandler.types;

import org.ships.block.blockhandler.Container;

public class Chest extends Container<org.bukkit.block.Chest> {
	
	@Override
	public void saveInventory() {
		this.inventory = getState().getBlockInventory();
	}
	
	@Override
	public void applyInventory() {
		for(int A = 0; A < this.inventory.getSize(); A++) {
			getState().getInventory().setItem(A, this.inventory.getItem(A));
		}
	}

}
