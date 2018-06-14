package org.ships.block.blockhandler;

import org.bukkit.block.BlockState;

public interface TextHandler<T extends BlockState> extends BlockHandler<T> {
	
	public void saveText();
	public void applyText();
	
	@Override
	public default BlockPriority getPriority() {
		return BlockPriority.SPECIAL;
	}


}
