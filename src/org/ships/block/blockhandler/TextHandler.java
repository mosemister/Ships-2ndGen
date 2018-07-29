package org.ships.block.blockhandler;

import org.bukkit.block.BlockState;

public interface TextHandler<T extends BlockState> extends BlockHandler<T> {
	public void saveText();

	public void applyText(T var1);

	@Override
	public default void apply(T blockstate) {
		this.applyText(blockstate);
	}

	@Override
	public default void save(boolean forRemoval) {
		this.saveBlockData();
		this.saveText();
	}

	@Override
	public default BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}
}
