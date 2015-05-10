package MoseShipsBukkit.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

public class BlockStack {
	
	List<Block> BLOCKS = new ArrayList<Block>();
	
	public void addBlock(Block block){
		BLOCKS.add(block);
	}
	
	public List<Block> getList(){ 
		return BLOCKS;
	}
	
	public boolean contains(Block block){
		for(Block block2 : getList()){
			if (block2.getLocation().equals(block.getLocation())){
				return true;
			}
		}
		return false;
	}

}
