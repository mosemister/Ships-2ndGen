package MoseShipsBukkit.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import MoseShipsBukkit.StillShip.Vessel;

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
	
	public boolean isVaild(){
		for(Block block : getList()){
			if (block.getState() instanceof Sign){
				Sign sign = (Sign)block.getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")){
					Vessel vessel = Vessel.getVessel(sign);
					if (vessel == null){
						return false;
					}else{
						return true;
					}
				}
			}
		}
		return false;
	}
}
