package MoseShipsBukkit.BlockHandler.Types;

import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShipsBukkit.BlockHandler.BlockHandler;

public class DefaultBlockHandler implements BlockHandler {

	Block block;
	
	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public void remove(Material material) {
		this.block.setType(material);
	}

	@Override
	public void apply() {
	}

}
